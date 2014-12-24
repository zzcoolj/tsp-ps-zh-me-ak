package tsp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
		
		public Double[][] toTab()
		{
			
			Double[][] tab = new Double[couts.size()][couts.size()];
			
			for(Entry<Vertex, ArrayList<Double>> entry : couts.entrySet()) {
			    Vertex key = entry.getKey();
			    ArrayList<Double> values = entry.getValue();
			    
			    int j = 0;
			    for(Double d : values)
			    {
			    	tab[key.getNumero()][j] = d;
			    	j++;
			    }
			    // do what you have to do here
			    // In your case, an other loop.
			}
			
			return tab;
		}
		
		
}
