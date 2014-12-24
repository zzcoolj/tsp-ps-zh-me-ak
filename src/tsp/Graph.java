package tsp;
import java.util.ArrayList;



public class Graph {

		private ArrayList<Vertex> villes;
		
		public Graph(ArrayList<Vertex> cities){
			this.villes = cities;
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
