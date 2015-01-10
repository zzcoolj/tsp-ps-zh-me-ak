package CustomClass;

import tsp.Vertex;

public class PaireLamdbaRho extends Paire<Double, Double> {
	
	public PaireLamdbaRho(Double lambda, Double rho) {
		super(lambda, rho);
	}
	
	public Double getLambda(){
		return getFirst();
	}
	
	public Double getRho(){
		return getSecond();
	}
}
