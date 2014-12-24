package tsp;

public class Scenario {
	
	private Integer numero;
	private Graph solution;
	
	public Scenario(int numero)
	{
		this.numero = numero;
		
		//TODO null ou on commence penalite + VNS 
		solution = null; 
	}
	
	public void solve()
	{
		
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public void setSolution(Graph solution) {
		this.solution = solution;
	}
	public Integer getNumero() {
		return numero;
	}
	public Graph getSolution() {
		return solution;
	}
	
	

}
