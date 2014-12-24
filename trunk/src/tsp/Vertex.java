package tsp;
import java.awt.geom.Point2D;


public class Vertex {
	private int numero;
	private Point2D position; 
	
	public Vertex(int n){
		this.numero = n;
	}
	
	public int getNumero(){
		return this.numero;
	}
	
	public void setPosition(double x, double y){
		this.position.setLocation(x, y);
	}
	
	public Point2D getPosition(){
		return this.position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (numero != other.numero)
			return false;
		return true;
	}
	
	
	
}
