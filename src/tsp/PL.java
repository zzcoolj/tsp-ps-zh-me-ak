package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import CustomClass.PaireVertex;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;
import mvc.GraphOpt;
/**
 * Classe Programme Linéaire
 * 
 * @author Amin Zheng Gishan
 * 
 */
public class PL {

	int n;

	public PL()
	{
		
	}
	
	public GraphOpt solve()
	{
		return null;
		
	}
	
	public Double generateRho()
	{
		return null;
	}
	
	public Double generateLambda()
	{
		return null;
	}
	
	public void initScenario(ArrayList<Scenario> s, TSP tsp)
	{
		for (Scenario scenario : s) {
			Graph g = new Graph(tsp.getG().getVilles());
			LinkedHashMap<PaireVertex, Double> tmp = new LinkedHashMap<>();
			
			for(int i=0; i<g.getNbVilles(); i++){
				for(int j=0; j<g.getNbVilles();j++){
					PaireVertex p = new PaireVertex(new Vertex(i),new Vertex(j));
					if(tsp.getDeterminists().contains(p) || p.hasSameVertex()){
						tmp.put(p, tsp.getG().getCouts().get(p));
					}
					else{
						Double valeurXml = tsp.getG().getCouts().get(p);
						Double ecartype = Maths.calculEcartType(tsp);
						int nbvaleur = 3;
						
						Double min = valeurXml-ecartype;
						Double max = valeurXml+ecartype;
						try {
							System.out.println(Maths.generateRandomCosts(valeurXml, nbvaleur, min, max));
						} catch (ExceptionMaths e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			g.setCouts(tmp);
			scenario.setSolution(g);
		}
	}
	
	public Graph generateSolutionReference(Graph g)
	{
		
		return null;
	}
	
	public Graph glouton(Graph g)
	{
		List<Map.Entry<PaireVertex, Double>> entries = new ArrayList<Map.Entry<PaireVertex, Double>>(g.getCouts().entrySet());
		
		Map<PaireVertex, Double> sortedMap = sort(entries);
		
		ArrayList<PaireVertex> aretesChemin = new ArrayList<PaireVertex>();
		

		return null;
	}
	
	private Map<PaireVertex, Double> sort(List<Map.Entry<PaireVertex, Double>> entries)
	{
		Collections.sort(entries, new Comparator<Map.Entry<PaireVertex, Double>>() {

			@Override
			public int compare(Entry<PaireVertex, Double> o1, Entry<PaireVertex, Double> o2) {
				if(o1.getValue()<=o2.getValue())
					return -1;
				else
					return 1;
			}
			
		});
		
		Map<PaireVertex, Double> sortedMap = new LinkedHashMap<PaireVertex, Double>();
		for (Map.Entry<PaireVertex, Double> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
			//System.out.println("Paire = "+entry.getKey()+" value = "+entry.getValue());
		}
		return sortedMap;
	}

}
