package tsp;

import java.util.ArrayList;

import CustomClass.PaireVertex;
import mvc.GraphOpt;

public class TSP {
	
	private PL pl;
	private Graph g;
	private Parser p;
	private ArrayList<Scenario> s;
	private int scenario=10;
	private float pourcentageDeterminist;
	protected static Integer n_opt = 2;
	
	public TSP(String nameXml) {
		p = new Parser(nameXml,"");
		g = new Graph(null);
		p.parse(g);
		s = new ArrayList<Scenario>();

		pl = new PL();
		pl.initScenario(s, this, scenario);

		System.out.println("Glouton en cours");
		long startTime = System.nanoTime();
		pl.glouton(g);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("timer glouton : "+duration+" ms");
		
		
		//System.out.println("Contains = "+g.getCouts().containsKey(new PaireVertex(new Vertex(17), new Vertex(17))));
		
		//TODO regarder cela 

	}
	
	public GraphOpt launch() 
	{
		//Double[][] tab = d.toTab();
		
		//pl = new PL(tab);
		
		
		long startTime = System.currentTimeMillis();
		GraphOpt result = pl.solve();
		long stopTime = System.currentTimeMillis();
		System.out.println("timer = "+(stopTime - startTime)/1000);
		result.setTime((stopTime - startTime));

		return result;
		
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

	


}
