package tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

import CustomClass.HashLambdaRho;
import CustomClass.PaireLamdbaRho;
import CustomClass.PaireVertex;
import Math.Maths;
import mvc.GraphOpt;

public class TSP extends Observable implements Observer{

	
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
	private int scenario = 10;
	private float pourcentageDeterminist;
	protected static Integer n_opt = 2;

	//penalite.get(0) => array.get(0) => scenario 0 iteration 0
	private LinkedHashMap<Integer, ArrayList<HashLambdaRho>> penalite;
	
	
	public TSP(String nameXml) {
		p = new Parser(nameXml,"");
		//g = new Graph(null);
		g = new Graph();
		p.parse(g);
		s = new ArrayList<Scenario>();
		pl = new PL();	
		penalite = new LinkedHashMap<Integer, ArrayList<HashLambdaRho>>();
	}
	
	public GraphOpt launch(float determinist, Integer kmax, Integer nbScenario) 
	{
		penalite.clear();
		s.clear();
		
		scenario = nbScenario;
		n_opt = kmax;
		pourcentageDeterminist = determinist;
		
		pl.initDeterminist(g, pourcentageDeterminist);
		
		System.out.println("J'ai choisis comme deterministe : "+g.getDeterminists());
		
		System.out.println("initialisation des scenario");
		pl.initScenario(s, this, scenario);
		
		
		
		System.out.println("====== "+s);
		System.out.println("Debut vrai");
		setChanged();
		notifyObservers(this.s);
		
		System.out.println("Fin Vrai");

		Graph reference = pl.glouton(g);
		System.err.println("pl.glouton )))))))))) = "+reference.coutSolution());
		reference.setDeterminists(getDeterministes(reference,g));
		
		for(Scenario sc : this.s)
		{
			sc.setSolution(pl.glouton(sc.getGeneral()));
		}
		
		/*try {
			s.get(0).getVns().algoVNSNopt(s.get(0),n_opt);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		boolean continuer = true;
		
		int iteration = 0;
		
		do{ 
			
			pl.algoPenalite(iteration, penalite, reference, g, s);
			
			for(Scenario scenario : s)
			{
				try {
					scenario.getVns().findBestSolution(scenario);
				} catch (CloneNotSupportedException e) {
					System.err.println("CloneNotSupported dans classe TSP");
					e.printStackTrace();
				}
			}
			
			System.out.println("Cout actuel : "+pl.fonctionObjectiveLocalResultat(s, penalite, reference));
			
			reference = fusion(reference);
			
			iteration++;
			
			if(!N_Iteration)
			{
				if(iteration==nbIteration)
				{
					continuer = false;
				}
			}
			
			System.out.println("\n\n\n-------------------------------NOUVELLE ITERATION +" + iteration + "-------------------------------\n\n\n");
		}while(!testArret(reference) && continuer);

		
		
		System.out.println("Fini");
		System.out.println("Cout final = "+reference.coutSolution());
		GraphOpt gopt = new GraphOpt();
		gopt.setCout(reference.coutSolution());
		gopt.setCheminVNS(reference.getCouts());
		
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
		return gopt;
		
	}
	
	private ArrayList<PaireVertex> getDeterministes(Graph reference, Graph g) {
		ArrayList<PaireVertex> listeDeterministe = new ArrayList<PaireVertex>();
		for(PaireVertex p : g.getDeterminists()){
			if(g.getCouts().containsKey(p)){
				listeDeterministe.add(p);
			}
		}
		return listeDeterministe;
	}

	
	public boolean testArret(Graph reference) {
		
		for(PaireVertex determinist : reference.getCouts().keySet())
		{
			if(reference.getDeterminists().contains(determinist))
			{
				for(Scenario scenario : s)
				{
					if(!scenario.getSolution().getCouts().containsKey(determinist))
					{
						//FIXME ne pas oublier de mettre false ici
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Graph fusion(Graph reference)
	{
		//somme xij si > 0.5 on prend l'arete
		//sinon on l'a prend pas
		//
		
		ArrayList<PaireVertex> paireReferenceXij = new ArrayList<PaireVertex>();
		ArrayList<PaireVertex> paireDisponible = new ArrayList<PaireVertex>();
		LinkedHashMap<PaireVertex, Double> coutsNouveau = new LinkedHashMap<PaireVertex, Double>();
		
		for(PaireVertex paire : g.getCouts().keySet())
		{
			if(!paire.hasSameVertex())
			{
				float xij = 0f;
				Double mincij = Double.MAX_VALUE;
				for(Scenario scenario : s)
				{
					if(scenario.getSolution().getCouts().containsKey(paire))
					{
						if(mincij > scenario.getSolution().getCouts().get(paire))
						{
							mincij = scenario.getSolution().getCouts().get(paire);
						}
						xij+=1;
					}
				}
				
				xij = ((float)xij)/s.size();
				
				if(xij>0.5)
				{
					//x->y on a pas le droit d'avoir z->y
					if(!pl.arcExisteDeja(paire.getFirst(), paire.getSecond(), paireReferenceXij))
					{
						paireReferenceXij.add(paire);
						coutsNouveau.put(paire, mincij);
					}
				}
				else
				{
					paireDisponible.add(paire);
				}
			}
		}
		
		System.out.println("PaireReference Avant "+paireReferenceXij);
		
		Vertex depart = paireReferenceXij.get(0).getFirst();
		
		for(PaireVertex paireD : paireDisponible)
		{
			if(!pl.arcExisteDeja(paireD.getFirst(), paireD.getSecond(), paireReferenceXij) && !paireD.getSecond().equals(depart))
			{
				paireReferenceXij.add(paireD);
				coutsNouveau.put(paireD, g.getCouts().get(paireD));
			}
		}
		
		paireReferenceXij.add(new PaireVertex(paireReferenceXij.get(paireReferenceXij.size()-1).getSecond(), depart));
		
		System.out.println("Nouveau chemin final "+paireReferenceXij);
		
		
		Graph dernier = new Graph(g.getVilles());
		dernier.setCouts(coutsNouveau);
		dernier.setDeterminists(reference.getDeterminists());
		return dernier;
	}

	public GraphOpt findBestSolution()
	{
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
	
	

	/*public void algorithmePenalite(int penalite, Graph reference){
		if(penalite == 0){
			int size = reference.getDeterminists().size();
			ArrayList<PaireLamdbaRho> test = new ArrayList<PaireLamdbaRho>();
			Double max = getMaxValue(g);
			for(int i=0; i<size;i++){
				PaireVertex paire = reference.getDeterminists().get(i);
				Double cout = g.getCouts().get(paire).doubleValue();
				test.add(new PaireLamdbaRho(max, cout));
			}
		}
		
		
	}*/

	@Override
	public void update(Observable o, Object arg) {
		
		try {
			Scenario updated = (Scenario)arg;
			
			setChanged();
			notifyObservers(updated);
		} catch (ClassCastException e) {
			setChanged();
			notifyObservers(arg);
		}
		
	}
	


}
