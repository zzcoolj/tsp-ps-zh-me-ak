package tsp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import CustomClass.PaireVertex;

public class Graph {

		private ArrayList<Vertex> villes;
		private LinkedHashMap<PaireVertex, Double> couts;
		
		public Graph(ArrayList<Vertex> cities){
			this.couts = new LinkedHashMap<PaireVertex, Double>();
			this.villes = cities;
		}
		
		public LinkedHashMap<PaireVertex, Double> getCouts() {
			// TODO Auto-generated method stub
			return this.couts;
		}
		
		public void setCities(ArrayList<Vertex> cities){
			this.villes = cities;
		}
		
		
		public void affiche(){
			 for (Vertex element : villes) {
				System.out.println(element.getNumero());
			}
	
		}

		public ArrayList<Vertex> getVilles() {
			return villes;
		}

		public int getNbVilles() {
			return villes.size();
		}
		
		public Double[][] toTab()
		{
			
			Double[][] tab = new Double[couts.size()][couts.size()];
			
			for(Entry<PaireVertex, Double> entry : couts.entrySet())
			{
				PaireVertex key = entry.getKey();
				Double value = entry.getValue();
				
				tab[key.getFirst().getNumero()][key.getSecond().getNumero()] = value;
			}
			
			return tab;
		}
		
}
