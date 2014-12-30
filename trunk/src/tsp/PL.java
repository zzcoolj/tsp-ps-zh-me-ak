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
		
		ArrayList<PaireVertex> cheminGlouton = new ArrayList<PaireVertex>();
		
		ArrayList<Vertex> departs = new ArrayList<Vertex>();
		ArrayList<Vertex> arrivee = new ArrayList<Vertex>();
		
		g.getDeterminists().add(new PaireVertex(new Vertex(0), new Vertex(1)));
		g.getDeterminists().add(new PaireVertex(new Vertex(1), new Vertex(2)));
		
		
		
		for(PaireVertex pv : g.getDeterminists())
		{
			departs.add(pv.getFirst());
			arrivee.add(pv.getSecond());
		}
		
		Vertex magique = new Vertex(departs.get(0).getNumero());
		System.out.println("Magique : "+magique);
		
		for(Entry<PaireVertex, Double> entry : sortedMap.entrySet())
		{
			if(!departs.contains(entry.getKey().getFirst()) && !arrivee.contains(entry.getKey().getSecond()) && !entry.getKey().getSecond().equals(magique))
			{
				if(!arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), departs, arrivee))
				{
					departs.add(entry.getKey().getFirst());
					arrivee.add(entry.getKey().getSecond());
				}
				
			}
		}
		
		for(Entry<PaireVertex, Double> entry : sortedMap.entrySet())
		{
			if(entry.getKey().getSecond().equals(magique) && !departs.contains(entry.getKey().getFirst()) && !arrivee.contains(entry.getKey().getSecond()))
			{
				if(!arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), departs, arrivee))
				{
					departs.add(entry.getKey().getFirst());
					arrivee.add(entry.getKey().getSecond());
				}
				
			}
		}
		
		System.out.println("Chemin glouton");
		
		for (int i = 0; i < departs.size(); i++) {
			
			PaireVertex tmp = new PaireVertex(departs.get(i), arrivee.get(i));
			cheminGlouton.add(tmp);
			System.out.println(departs.get(i)+"->"+arrivee.get(i)+" couts = "+sortedMap.get(tmp));
		}
		

		return null;
	}
	
	private boolean arcExisteDeja(Vertex x, Vertex y, ArrayList<Vertex> departs, ArrayList<Vertex> arrivee)
	{
		int i = 0;
		for(Vertex vd : departs)
		{
			if(vd.equals(y))
			{
				return arrivee.get(i).equals(x);
			}
			
			i++;
		}
		
		return false;
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
