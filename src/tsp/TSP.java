package tsp;

import java.util.ArrayList;

import CustomClass.PaireVertex;
import mvc.GraphOpt;

public class TSP {
	
	private PL pl;
	private Graph g;
	private Parser p;
	private ArrayList<Scenario> s;
	
	private float pourcentageDeterminist;
	protected static Integer n_opt = 2;
	
	public TSP(String nameXml) {
		p = new Parser(nameXml,"");
		System.out.println("1");
		g = new Graph(null);
		System.out.println("2");
		p.parse(g);
		System.out.println("3");
		s = new ArrayList<Scenario>();

		System.out.println("4");
		pl = new PL();

		System.out.println("Avant glouton");
		
		long startTime = System.nanoTime();
		pl.glouton(g);
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("timer : "+duration+" ms");
		
		
		//System.out.println("Contains = "+g.getCouts().containsKey(new PaireVertex(new Vertex(17), new Vertex(17))));
		
		//TODO Ici ou plus tard
		//pl.initScenario(s);

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
