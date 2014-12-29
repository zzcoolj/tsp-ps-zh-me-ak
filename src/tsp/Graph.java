package tsp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import CustomClass.PaireVertex;

public class Graph {

		private ArrayList<Vertex> villes;
		private LinkedHashMap<PaireVertex, Double> couts;
		private ArrayList<PaireVertex> determinists;
		
		public Graph(ArrayList<Vertex> cities){
			this.couts = new LinkedHashMap<PaireVertex, Double>();
			this.villes = cities;
			this.determinists = new ArrayList<PaireVertex>();
		}
		
		public ArrayList<PaireVertex> getDeterminists() {
			return determinists;
		}

		public void setDeterminists(ArrayList<PaireVertex> determinists) {
			this.determinists = determinists;
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
		
		public void setCouts(LinkedHashMap<PaireVertex, Double> couts) {
			this.couts = couts;
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
