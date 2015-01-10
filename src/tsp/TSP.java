package tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;

import CustomClass.PaireLamdbaRho;
import CustomClass.PaireVertex;
import mvc.GraphOpt;

public class TSP extends Observable{
	
	private PL pl;
	private Graph g;
	private Parser p;
	private ArrayList<Scenario> s;
	private int scenario = 10;
	private float pourcentageDeterminist;
	protected static Integer n_opt = 2;
	private LinkedHashMap<Integer, ArrayList<PaireLamdbaRho>> penalite;
	
	
	public TSP(String nameXml) {
		p = new Parser(nameXml,"");
		//g = new Graph(null);
		g = new Graph();
		p.parse(g);
		s = new ArrayList<Scenario>();
		pl = new PL();	
		penalite = new LinkedHashMap<Integer, ArrayList<PaireLamdbaRho>>();
	}
	
	public GraphOpt launch(float determinist, Integer kmax, Integer nbScenario) 
	{
		scenario = nbScenario;
		n_opt = kmax;
		pourcentageDeterminist = determinist;
		
		pl.initDeterminist(g, pourcentageDeterminist);
		
		System.out.println("initialisation des scenario");
		pl.initScenario(s, this, scenario);
		
		System.out.println("====== "+s);
		System.out.println("Debut vrai");
		setChanged();
		notifyObservers(this.s);
		
		System.out.println("Fin Vrai");

		Graph reference = pl.glouton(g);
		reference.setDeterminists(getDeterministes(reference,g));
		
		for(Scenario sc : this.s)
		{
			sc.setSolution(pl.glouton(sc.getSolution()));
		}
		boolean continuer = true;
		
		int iteration = 0;
		
		do{ 
			for(Scenario sc : this.s)
			{
				sc.setSolution(pl.glouton(sc.getSolution()));
			}
			iteration++;
		}while(testArret());

		
		
	
		
		
		
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
	
	public Double getMaxValue(Graph g) {
		Double maxi = 0.0;
		for(Double max : g.getCouts().values()){
			if(max>maxi){
				maxi = max;
			}
		}
		return maxi;
	}

	public void algorithmePenalite(int penalite, Graph reference){
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
		
		
	}
	


}
