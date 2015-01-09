package tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import mvc.GraphOpt;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;

/**
 * Classe Programme Linéaire
 * 
 * @author Amin Zheng Gishan
 * 
 */
public class PL {

	int n;

	public PL() {

	}

	public GraphOpt solve() {
		return null;

	}

	public Double generateRho() {
		return null;
	}

	public Double generateLambda() {
		return null;
	}

	public void initScenario(ArrayList<Scenario> s, TSP tsp, int n) {
		
		s.clear();
		for(int i = 0; i < n; i++)
		{
			s.add(new Scenario(i));
		}
		
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
						
						Double min = valeurXml-ecartype;
						Double max = valeurXml+ecartype;
						try {
							Double rand = Maths.generateRandomCosts(valeurXml, min, max);
							tmp.put(p, rand);	
						} catch (ExceptionMaths e) {
							e.printStackTrace();
						}
					}
				}
			}
			g.setCouts(tmp);
			scenario.setSolution(g);
		}
	}

	public Graph generateSolutionReference(Graph g) {

		return null;
	}

	public Graph glouton(Graph g) {
		Vertex depart = g.getVilles()
				.get((int) Math.random() * g.getNbVilles());
		ArrayList<PaireVertex> cheminInterdit = new ArrayList<PaireVertex>();
		Vertex vilaInterdit = depart;
		for (int i = 0; i < g.getNbVilles()-1; i++) {
			PaireVertex tmp = null;
			if(i==0) {
				 tmp = procheVoisin(depart, g.getCouts(), cheminInterdit,null);
				 
			}
			else{
				tmp = procheVoisin(depart, g.getCouts(), cheminInterdit, vilaInterdit);
			}
			
			cheminInterdit.add(tmp);
			depart = tmp.getSecond();
		}
		Vertex sortante = cheminInterdit.get(cheminInterdit.size()-1).getSecond();
		Vertex entrante = cheminInterdit.get(0).getFirst(); 
		
		cheminInterdit.add(new PaireVertex(sortante,entrante));
		System.out.println(cheminInterdit);
		
		Graph graphe = new Graph(g.getVilles());
		LinkedHashMap<PaireVertex, Double> map = new LinkedHashMap<PaireVertex, Double>();
		for (PaireVertex p : cheminInterdit) {
			map.put(p, g.getCouts().get(p).doubleValue());
		}
		graphe.setCouts(map);

		return graphe;
	}

	public PaireVertex procheVoisin(Vertex depart,
			LinkedHashMap<PaireVertex, Double> map,
			ArrayList<PaireVertex> cheminInterdit, Vertex interdit) {
		PaireVertex voisin = null;
		double min;
		/**
		 * Correspond au premier coup
		 */
		ArrayList<PaireVertex> liste = new ArrayList<PaireVertex>();
		for (PaireVertex p : map.keySet()) {
			if(interdit == null){
				if (p.getFirst().equals(depart)
						&& !p.getSecond().equals(depart)
						&& !arcExisteDeja(p.getFirst(), p.getSecond(),
								cheminInterdit)) {
					liste.add(p);
				}
			}
			else{
				if (p.getFirst().equals(depart)
						&& !p.getSecond().equals(depart)
						&& !arcExisteDeja(p.getFirst(), p.getSecond(),
								cheminInterdit) && !p.getSecond().equals(interdit)) {
					liste.add(p);
				}
			}
		}
		min = Double.MAX_VALUE;
		//System.out.println("---"+liste+"  ville depart"+depart);
		for (PaireVertex p : liste) {
			if (min > map.get(p).doubleValue()) {
				min = map.get(p).doubleValue();
				voisin = p;
			}
		}
		return voisin;
	}

	private boolean arcExisteDeja(Vertex x, Vertex y,
			ArrayList<PaireVertex> array) {
		for (PaireVertex vd : array) {
			if(vd.getFirst().equals(x)){
				return true;
			}
			if (vd.equals(new PaireVertex(y, x))) {
				return true;
			}

			if (vd.getSecond().equals(y)) {
				return true;
			}

		}
		return false;
	}
}
