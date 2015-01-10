package tsp;

import java.util.Observable;

import tsp.Scenario.etat;

public class Scenario extends Observable{
	
	public enum etat{
		FINISHED,
		WAITING,
		SHAKING,
		CHANGINGNEIGHBORHOOD;
	}
	
	private Integer numero;
	private Graph solution;
	private Graph general;
	private etat e;
	private VNS vns;
	
	//TODO METTRE UN ETAT pour le scenario
	
	public Scenario(int numero)
	{
		this.numero = numero;
		e = etat.WAITING;
		//TODO null ou on commence penalite + VNS 
		solution = new Graph(); 
		general = new Graph();
		vns = new VNS();
	}
	
	public Graph getGeneral() {
		return general;
	}

	public void setGeneral(Graph general) {
		this.general = general;
	}

	public void solve()
	{
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null)
			return false;
		if(obj==this)
			return true;
		
		
		try {
			
			Scenario second = (Scenario)obj;
			
			return second.getNumero().equals(numero);
			
		} catch (ClassCastException e) {
			return false;
		}
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
		setChanged();
		notifyObservers(this);
	}

	public VNS getVns() {
		return vns;
	}

	public void updateEtat(etat e) {
		this.e = e;
	}
	
	
	@Override
	public String toString() {
		return "Scenario "+ numero 
				+ "Etat = "+e;
	}
	

}
