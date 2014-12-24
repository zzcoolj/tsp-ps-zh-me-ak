import java.util.ArrayList;


public class Maths {

	/**
	 * genere une liste de valeur random
	 * @param initial : valeur deterministe
	 * @param n : nombre de valeur retourne = nombre de scenario
	 * @return
	 */
	public static ArrayList<Double> generateRandomCosts(int initial, int n)
	{
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for(int i=0; i<n; i++)
		{
			//TODO a changer
			values.add(Math.random());
		}
		
		return values;
	}
	
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
