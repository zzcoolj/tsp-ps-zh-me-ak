package tsp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
	
	public TSP(String nameXml) {
		p = new Parser(nameXml,"");
		g = new Graph(null);
		p.parse(g);
		s = new ArrayList<Scenario>();
		pl = new PL();	}
	
	public GraphOpt launch(float determinist, Integer kmax, Integer nbScenario) 
	{
		scenario = nbScenario;
		n_opt = kmax;
		pourcentageDeterminist = determinist;
		
		System.out.println("initialisation des scenario");
		pl.initScenario(s, this, scenario);
		
		System.out.println("====== "+s);
		System.out.println("Debut vrai");
		setChanged();
		notifyObservers(this.s);
		
		System.out.println("Fin Vrai");

		
		Scenario sc = this.s.get(0);
		
		sc.setSolution(pl.glouton(sc.getSolution()));
		try {
			sc.getVns().algoVNSNopt(sc.getSolution(), this);
		} catch (CloneNotSupportedException e) {
			System.err.println("Une erreur innatendu est survenu : relancer le programme");
		}
		
		
		/*for(Scenario sc : this.s)
		{
			sc.setSolution(pl.glouton(sc.getSolution()));
			sc.getVns().algoVNSNopt(sc.getSolution(), this);
		}*/
		
		
		
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
