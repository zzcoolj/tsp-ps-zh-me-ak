package CustomClass;

import java.util.LinkedHashMap;

import tsp.Scenario;

public class HashLambdaRho extends LinkedHashMap<PaireVertex, PaireLamdbaRho>{
	
	private Scenario s;
	public HashLambdaRho(Scenario s) {
		super();
		this.s = s;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(java.util.Map.Entry<PaireVertex, PaireLamdbaRho> entry : this.entrySet())
		{
			result+="(" + entry.getKey() + ")"
					+ " -> ("+ entry.getValue() + ")\n";
		}
			
		return result;
	}

}
