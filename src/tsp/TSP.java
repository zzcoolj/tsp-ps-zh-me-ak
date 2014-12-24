package tsp;

import java.io.IOException;

import mvc.GraphOpt;

public class TSP {
	
	private PL pl;
	private Graph g;
	private Parser p;
	
	public TSP(String nameXml) {
		// TODO Auto-generated constructor stub
		p = new Parser(nameXml,"");
		g = new Graph(null);
		p.parse(g);

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
	
	
	public PL getPl() {
		return pl;
	}

	public Graph getG() {
		return g;
	}

	public Parser getParser() {
		return p;
	}



}
