import java.util.ArrayList;
import java.util.Random;

import tsp.TSP;


public class Maths {

	/**
	 * genere une liste de valeur random
	 * @param initial : valeur deterministe
	 * @param n : nombre de valeur retourne = nombre de scenario
	 * @return
	 */
	public static ArrayList<Double> generateRandomCosts(Double initial, int n, Double min, Double max)
	{
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for(int i=0; i<n; i++)
		{
			//TODO a changer
			Random rand = new Random();
			values.add(min + (max - min) * rand.nextDouble());
		}
		
		return values;
	}
	
	/*public static void main(String[] args) {
		
		Double min = 15-5.16;
		Double max = 15+5.16;
		System.out.println(Maths.generateRandomCosts(15.0, 5, min, max));
	}*/
	
	
	/**
	 * calcul l'ecart type : Ecart-Type = sqrt(variance)
	 * @param variance
	 * @return
	 */
	public static Double calculEcartType(Double variance)
	{
		return Math.sqrt(variance);
	}
	
	/**
	 * calcul la variance : Variance = E(X^2)-E(X)^2
	 * @return
	 */
	public static Double calculVariance(TSP tsp)
	{
		int taille = tsp.getG().getNbVilles();
		double xi[] = new double[taille];
		float pi[] = new float[taille];
		
		float ps = 1/(tsp.getS().size());
		
		for(int i=0;i<taille;i++)
		{
			pi[i] = ps;
		}
		
		Double sum = 0.0;
		
		//E(X)
		Double esperance = 0.0;
		for(int i=0;i<taille;i++)
		{
			sum += xi[i];
		}
		
		//E(X^2)
		sum = 0.0;
		for(int i=0;i<taille;i++)
		{
			sum += Math.pow(xi[i], 2);
		}
		Double esperance1 = sum*ps;
		
		//E(X)^2
		Double esperance2 = Math.pow(esperance, 2);
		
		//TODO return 3*variance ici ou plus tard ?
		return esperance1-esperance2;
	}
}
