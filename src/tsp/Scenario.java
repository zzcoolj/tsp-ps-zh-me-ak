package tsp;

public class Scenario {
	
	public enum etat{
		FINISHED,
		WAITING,
		SHAKING,
		CHANGINGNEIGHBORHOOD;
	}
	
	private Integer numero;
	private Graph solution;
	private etat e;
	
	//TODO METTRE UN ETAT pour le scenario
	
	public Scenario(int numero)
	{
		this.numero = numero;
		e = etat.WAITING;
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
	
	public etat getEtat()
	{
		return e;
	}
	
	public void setEtat(etat e)
	{
		this.e = e;
	}
	

}
