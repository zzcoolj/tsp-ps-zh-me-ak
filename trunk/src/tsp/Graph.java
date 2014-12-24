package tsp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Graph {

		private ArrayList<Vertex> villes;
		private LinkedHashMap<Vertex, ArrayList<Double>> couts;
		
		public Graph(ArrayList<Vertex> cities){
			this.couts = new LinkedHashMap<Vertex, ArrayList<Double>>(); 
			this.villes = cities;
		}
		
		public LinkedHashMap<Vertex, ArrayList<Double>> getCouts() {
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
		
		
}
