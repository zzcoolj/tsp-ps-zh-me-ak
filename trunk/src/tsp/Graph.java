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
		
		public Graph(){
			this.couts = new LinkedHashMap<PaireVertex, Double>();
			this.determinists = new ArrayList<PaireVertex>();
			this.villes = new ArrayList<Vertex>();
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
			
			for(PaireVertex paire : couts.keySet())
			{
				if(!villes.contains(paire.getFirst()))
					villes.add(paire.getFirst());
			}
		}

		public Double[][] toTab()
		{
			System.out.println("Debut totab");
			Double[][] tab = new Double[villes.size()][villes.size()];
			
			for(Entry<PaireVertex, Double> entry : couts.entrySet())
			{
				PaireVertex key = entry.getKey();
				Double value = entry.getValue();
				
				tab[key.getFirst().getNumero()][key.getSecond().getNumero()] = value;
			}
			System.out.println("Fin totab");
			return tab;
		}
		
		@Override
		public String toString() {
		
			String retour = "";
			
			for(Entry<PaireVertex, Double> entry : couts.entrySet())
			{
				retour += entry.getKey()+" = "+entry.getValue()+"\n";
			}
			
			return retour;
			
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			
			Graph copie = new Graph();

			for(Vertex v : villes)
			{
				copie.villes.add((Vertex) v.clone());
			}
			
			for(PaireVertex paire : determinists)
			{
				copie.determinists.add((PaireVertex) paire.clone());
			}
			
			for(Entry<PaireVertex,Double> entry : couts.entrySet())
			{
				copie.couts.put((PaireVertex)entry.getKey().clone(), entry.getValue());
			}
			
			return copie;
		}
		
}
