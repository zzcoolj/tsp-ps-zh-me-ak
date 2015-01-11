package tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

import CustomClass.HashLambdaRho;
import CustomClass.PaireLamdbaRho;
import CustomClass.PaireVertex;
import mvc.GraphOpt;

public class TSP extends Observable implements Observer{
	
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
			
			iteration++;
			
			System.err.println("---------------------------------------------------------------------------");
			
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
			
			iteration++;
		}while(testArret());

		
		System.out.println("Fini");
	
		
		
		
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

		return null;
		
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

	
	public boolean testArret() {
		return false;
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
			// TODO: handle exception
		}
		
	}
	


}
