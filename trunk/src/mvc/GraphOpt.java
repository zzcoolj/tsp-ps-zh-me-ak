package mvc;

import java.util.HashMap;
import java.util.LinkedList;

public class GraphOpt {

	private double cout;
	private long time;
	private LinkedList<Integer> chemin;
	private HashMap<Integer, LinkedList<Integer>> listSubtours;
	
	

	public GraphOpt() {
		cout = 0;
		time = 0;
		this.listSubtours = new HashMap<Integer, LinkedList<Integer>>();
		this.chemin = new LinkedList<Integer>();
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setCout(double d) {
		this.cout = d;
	}

	public void setChemin(LinkedList<Integer> chemin) {
		this.chemin = chemin;
	}

	public double getCout() {
		return cout;
	}

	public LinkedList<Integer> getChemin() {
		return chemin;
	}

	public HashMap<Integer, LinkedList<Integer>> getListSubtours() {
		return listSubtours;
	}

}
