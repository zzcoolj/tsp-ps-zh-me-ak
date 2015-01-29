package mvc;

import java.util.Observable;

import tsp.TSP;

public class Model extends Observable{
	
	private TSP tsp;
	
	public Model() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void changeXML(String nameXml)
	{
		tsp = new TSP(nameXml);
		launch(tsp);
		
	}
	
	public void tspSolve(float determinist, Integer kmax, Integer nbScenario)
	{
		if(tsp==null)
			return;
		
		GraphOpt opt = new GraphOpt();
		opt = tsp.launch(determinist, kmax, nbScenario);
		launch(opt);
	}
	
	public void launch(Object o)
	{
		setChanged();
		notifyObservers(o);
	}

	public void launch()
	{
		setChanged();
		notifyObservers(tsp);
	}
	
	
	public TSP getTsp() {
		return tsp;
	}
	
	

}
