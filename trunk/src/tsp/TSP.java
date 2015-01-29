package tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import CustomClass.HashLambdaRho;
import CustomClass.PaireVertex;
import mvc.GraphOpt;

public class TSP extends Observable implements Observer {

	/**
	 * 
	 * 
	 * METTRE A FALSE POUR : Pour s'arreter a nbIteration 
	 * METTRE A TRUE POUR : Faire N iteration jusqu'a la fin
	 * 
	 */

	private boolean N_Iteration = false;
	private int nbIteration = 100;
	
	
	private PL pl;
	private Graph g;
	private Parser p;
	private ArrayList<Scenario> s;
	private int nbscenario = 10;
	private float pourcentageDeterminist;
	protected static Integer n_opt = 2;

	// penalite.get(0) => array.get(0) => scenario 0 iteration 0
	private LinkedHashMap<Integer, ArrayList<HashLambdaRho>> penalite;

	public TSP(String nameXml) {
		p = new Parser(nameXml, "");
		// g = new Graph(null);
		g = new Graph();
		p.parse(g);
		s = new ArrayList<Scenario>();
		pl = new PL();
		penalite = new LinkedHashMap<Integer, ArrayList<HashLambdaRho>>();
	}

	public GraphOpt launch(float determinist, Integer kmax, Integer nbScenario) {
		penalite.clear();
		s.clear();

		nbscenario = nbScenario;
		n_opt = kmax;
		pourcentageDeterminist = determinist;

		pl.initDeterminist(g, pourcentageDeterminist);

		pl.initScenario(s, this, nbscenario);
		setChanged();
		notifyObservers(this.s);

		Graph reference = new Graph(); // pl.glouton(g);
		reference.setDeterminists(getDeterministes(reference, g));

		for (Scenario sc : this.s) {
			sc.setSolution(pl.glouton(sc.getGeneral()));
		}

		reference = fusion(reference);
		
		/////
		
		/*try {
			s.get(0).getVns().algoVNSNopt(s.get(0),n_opt);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ThreadMain tm = new ThreadMain(this,reference);
		tm.start();

		
		
		
		return null;

	}

	private ArrayList<PaireVertex> getDeterministes(Graph reference, Graph g) {
		ArrayList<PaireVertex> listeDeterministe = new ArrayList<PaireVertex>();
		for (PaireVertex p : g.getDeterminists()) {
			if (g.getCouts().containsKey(p)) {
				listeDeterministe.add(p);
			}
		}
		return listeDeterministe;
	}

	public boolean testArret(Graph reference, int iteration) {

		ArrayList<Integer> sum = new ArrayList<Integer>();
		int presenceSum = 0;
		boolean zeroD = false;
		for (PaireVertex determinist : reference.getCouts().keySet()) {
			int nb = 0;
			if (reference.getDeterminists().contains(determinist)) {
				zeroD = true;
				for (int i = 0; i < s.size(); i++) {
					if (s.get(i).getSolution().getCouts()
							.containsKey(determinist)
							&& s.get(i).getSolution().getDeterminists()
									.contains(determinist)) {
						nb++;
					}

				}
				sum.add(presenceSum, nb);
				presenceSum++;
			}
		}
		if (zeroD) {
			double finale = 0;
			for (Integer i : sum) {
				finale += i;
			}
			finale = (double) finale / (s.size() * presenceSum);
			if (finale >= 0.9) {
				return true;
			} else {
				return false;
			}
		} else
			return true;
	}

	public Graph fusion(Graph reference) {
		// somme xij si > 0.5 on prend l'arete
		// sinon on l'a prend pas
		//

		ArrayList<PaireVertex> paireReferenceXij = new ArrayList<PaireVertex>();
		ArrayList<PaireVertex> paireDisponible = new ArrayList<PaireVertex>();
		LinkedHashMap<PaireVertex, Double> coutsNouveau = new LinkedHashMap<PaireVertex, Double>();

		for (PaireVertex paire : g.getCouts().keySet()) {
			if (!paire.hasSameVertex()) {
				float xij = 0f;
				Double mincij = Double.MAX_VALUE;
				for (Scenario scenario : s) {
					if (scenario.getSolution().getCouts().containsKey(paire)) {
						if (mincij > scenario.getSolution().getCouts()
								.get(paire)) {
							mincij = scenario.getSolution().getCouts()
									.get(paire);
						}
						xij += 1;
					}
				}

				xij = ((float) xij) / s.size();
				if (xij > 0.9) {
					// x->y on a pas le droit d'avoir z->y
					PaireVertex tmp = pl.recursif(paireReferenceXij, paire, 0,
							paire);
					// //System.out.println("tmp.equals(paire)"+paire.equals(tmp)+"\n");
					if (!pl.arcExisteDeja(paire.getFirst(), paire.getSecond(),
							paireReferenceXij)) {
						// //System.err.println("J'ajoute " +paire);
						paireReferenceXij.add(paire);
						coutsNouveau.put(paire, mincij);
					}
				} else {
					if (!pl.arcExisteDeja(paire.getFirst(), paire.getSecond(),
							paireReferenceXij)) {
						paireDisponible.add(paire);
					}
				}
			}
		}

		// //System.out.println("PaireDisponible : "+paireDisponible);

		// //System.out.println("Reference  AVANT ?? "+paireReferenceXij);

		/*
		 * Vertex depart = paireReferenceXij.get(0).getFirst();
		 * 
		 * for(PaireVertex paireD : paireDisponible) {
		 * if(!pl.arcExisteDeja(paireD.getFirst(), paireD.getSecond(),
		 * paireReferenceXij) && !paireD.getSecond().equals(depart)) {
		 * paireReferenceXij.add(paireD); coutsNouveau.put(paireD,
		 * g.getCouts().get(paireD)); } }
		 * 
		 * try { paireReferenceXij.add(paireVertexDerniere(paireReferenceXij));
		 * } catch (Exception e) {
		 * 
		 * }
		 */

		ArrayList<PaireVertex> cheminReferenceFinal = new ArrayList<PaireVertex>();
		try {
			cheminReferenceFinal = pl.gloutonBis2(g, paireReferenceXij);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Nouveau chemin final "+cheminReferenceFinal);

		LinkedHashMap<PaireVertex, Double> cheminReferenceFinalCout = new LinkedHashMap<PaireVertex, Double>();

		for (PaireVertex paire : cheminReferenceFinal) {
			if (reference.getCouts().get(paire) != null) {
				cheminReferenceFinalCout.put(paire,
						reference.getCouts().get(paire));
			} else {
				cheminReferenceFinalCout.put(paire, g.getCouts().get(paire));
			}

		}

		// for(Entry<PaireVertex,Double> entry : referenceF)

		Graph dernier = new Graph(g.getVilles());
		dernier.setCouts(cheminReferenceFinalCout);
		dernier.setDeterminists(g.getDeterminists());

		return dernier;
	}

	public PaireVertex paireVertexDerniere(
			ArrayList<PaireVertex> paireReferenceXij) throws Exception {

		LinkedHashMap<Vertex, Boolean> sortante = new LinkedHashMap<Vertex, Boolean>();
		LinkedHashMap<Vertex, Boolean> entrante = new LinkedHashMap<Vertex, Boolean>();

		/**
		 * Init tout a false
		 */
		for (PaireVertex pv : g.getCouts().keySet()) {
			if (!pv.hasSameVertex() && paireReferenceXij.contains(pv)) {
				sortante.put(pv.getFirst(), false);
				sortante.put(pv.getSecond(), false);

				entrante.put(pv.getFirst(), false);
				entrante.put(pv.getSecond(), false);
			}
		}

		/**
		 * On boucle pour initialiser les vrai valeurs de entrante et sortante
		 */
		for (PaireVertex pv : paireReferenceXij) {
			sortante.replace(pv.getFirst(), true);
			entrante.replace(pv.getSecond(), true);
		}

		/**
		 * On boucle pour retrouver la vertex qui n'est jamais sortante et la
		 * vertex qui n'est jamais entrante
		 */

		Vertex e = null;
		Vertex s = null;

		for (Entry<Vertex, Boolean> entry : sortante.entrySet()) {
			if (entry.getValue() == false) {
				s = entry.getKey();
				break;
			}
		}

		for (Entry<Vertex, Boolean> entry : entrante.entrySet()) {
			if (entry.getValue() == false) {
				e = entry.getKey();
				break;
			}
		}

		if (s == null || e == null)
			throw new Exception(
					"Une erreur est intervenu nous n'avons pas pu completer la solution de reference car elle doit l'etre deja"
							+ " s = " + s + " e " + e);

		return new PaireVertex(s, e);
	}

	public GraphOpt findBestSolution() {
		return null;
	}

	public PL getPl() {
		return pl;
	}

	public Graph getG() {
		return g;
	}

	public Parser getParser() {
		return p;
	}

	public Parser getP() {
		return p;
	}

	public ArrayList<Scenario> getS() {
		return s;
	}

	public ArrayList<PaireVertex> getDeterminists() {
		return this.g.getDeterminists();
	}

	public float getPourcentageDeterminist() {
		return pourcentageDeterminist;
	}

	public static Integer getN_opt() {
		return n_opt;
	}

	/*
	 * public void algorithmePenalite(int penalite, Graph reference){
	 * if(penalite == 0){ int size = reference.getDeterminists().size();
	 * ArrayList<PaireLamdbaRho> test = new ArrayList<PaireLamdbaRho>(); Double
	 * max = getMaxValue(g); for(int i=0; i<size;i++){ PaireVertex paire =
	 * reference.getDeterminists().get(i); Double cout =
	 * g.getCouts().get(paire).doubleValue(); test.add(new PaireLamdbaRho(max,
	 * cout)); } }
	 * 
	 * 
	 * }
	 */

	@Override
	public void update(Observable o, Object arg) {

		try {
			Scenario updated = (Scenario) arg;

			setChanged();
			notifyObservers(updated);
		} catch (ClassCastException e) {
			setChanged();
			notifyObservers(arg);
		}

	}

	
	public class ThreadMain extends Thread
	{
		private TSP tsp;
		private Graph reference;
		public ThreadMain(TSP tsp, Graph reference) {
			this.tsp = tsp;
			this.reference = reference;
		}

		@Override
		public void run() {
			
			long startTime = System.nanoTime();
			
			int iteration = 0;
			boolean continuer = true;
			
			GraphOpt gopt = new GraphOpt();
			
			
			do{ 
				
				tsp.pl.algoPenalite(iteration, penalite, reference, g, s);
				
				tsp.pl.fonctionObjectiveLocalResultat(s, penalite, reference);
				for (Scenario scenario : s) {
					try {
						scenario.getVns().findBestSolution(scenario);
					} catch (CloneNotSupportedException e) {
						// System.err.println("CloneNotSupported dans classe TSP");
						e.printStackTrace();
					}
				}
				
				//System.out.println("Cout actuel : "+pl.fonctionObjectiveLocalResultat(s, penalite, reference));
				
				reference = fusion(reference);
				iteration++;
				
				setChanged();
				gopt.setCout(reference.coutSolution());
				gopt.setCheminVNS(reference.getCouts());
				notifyObservers(gopt);
				
				
				if(!N_Iteration)
				{
					if(iteration==nbIteration)
					{
						continuer = false;
					}
				}
				
				System.out.println("\n\n\n-------------------------------NOUVELLE ITERATION +" + iteration + "-------------------------------\n\n\n");
			}while(!testArret(reference, iteration - 1) && continuer);
			
			System.out.println("Fini");
			//System.out.println("Cout final = "+reference.coutSolution());
			
			gopt.setCout(reference.coutSolution());
			gopt.setCheminVNS(reference.getCouts());
			
			
			
			long endTime = System.nanoTime();
			long time = (endTime - startTime)/1000000000;
			if(time==0)
			{
				time = 1;
			}
			gopt.setTime(time);
			
			/*System.out.println("Glouton en cours");
			long startTime = System.nanoTime();
			pl.glouton(g);
			long endTime = System.nanoTime();

			long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
			System.out.println("timer glouton : "+duration+" ms");*/
			
			
			/*long startTime = System.currentTimeMillis();
			GraphOpt result = pl.solve();
			long stopTime = System.currentTimeMillis();
			System.out.println("timer = "+(stopTime - startTime)/1000);
			result.setTime((stopTime - startTime));*/
			setChanged();
			notifyObservers(gopt);
			
			for(int i = 0;i<10;i++)
			{
				System.out.println("\n\n\n");
			}
			System.out.println("////////////////////////////////////////////////////////////");
			System.out.println("********* Cout final "+ reference.coutSolution() +" *********");
			System.out.println("////////////////////////////////////////////////////////////");
		}
	}


}
