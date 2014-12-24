import java.util.ArrayList;
import java.util.Random;


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
	 * calcul l'ecart type
	 * @param variance
	 * @return
	 */
	public static Double calculEcartType(Double variance)
	{
		
		return null;
	}
	
	/**
	 * calcul la variance
	 * @return
	 */
	public static Double calculVariance()
	{
		
		//TODO return 3*variance ici ou plus tard ?
		return null;
	}
}
