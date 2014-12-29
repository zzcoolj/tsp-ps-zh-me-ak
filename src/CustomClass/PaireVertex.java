package CustomClass;

import java.util.LinkedHashMap;

import tsp.Vertex;

public class PaireVertex extends Paire<Vertex, Vertex>{

	public PaireVertex(Vertex a, Vertex b) {
		super(a, b);
		// TODO Auto-generated constructor stub
	}
	
	public boolean hasSameVertex()
	{
		return this.first.getNumero()==this.second.getNumero();
	}
	
	/*public static void main(String[] args) {
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		
		Vertex v3 = new Vertex(5);
		Vertex v4 = new Vertex(2);
		
		PaireVertex paire1 = new PaireVertex(v1, v2);
		
		PaireVertex paire2 = new PaireVertex(v3, v4);
		
		System.out.println(paire1.equals(paire2));
		
		LinkedHashMap<PaireVertex, Double> couts = new LinkedHashMap<PaireVertex, Double>();
		couts.put(paire1, 1.0);
		couts.put(paire2,3.0);
		
		
		System.out.println(couts.containsKey(new PaireVertex(new Vertex(3), new Vertex(1))));
	}*/

}
