package tsp;
/*
public class VNS {
	
	private Integer kmax;
	private Integer neighborhood;
	
	
	public Graph solve(Graph g)
	{
		return null;
	}
	
	private void neighborhoodChange()
	{
		
	}
	
	private void shake()
	{
		
	}

}
*/
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Random;

public class VNS {
	private int kmax;
	private int neighborhood;
	private TreeSet<Integer> shakeList;// !!!
	private int coutDeCheminExistePas = 999;

	// HashMap<Vertex, ArrayList<Double>> couts;
	Double[][] coutsTest;

	public VNS(int neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	private void shake(int nbVille) {
		shakeList = new TreeSet<Integer>();
		Random r = new Random();
		while (shakeList.size() < neighborhood) {
			shakeList.add(r.nextInt(nbVille));// [0, nbVille-1] !!! 若nbville 后面会爆掉
			System.out.println("shakeList size : " + shakeList.size()
					+ "\nnombre(s) choisi(s) : " + shakeList.toString());// for test
		}

		System.out.print("arretes choisis : ");// test
		Iterator<Integer> it = shakeList.iterator();// test
		while (it.hasNext()) {// test
			int i = it.next();// test
			if(i+1 < nbVille){
				System.out.print("[ " + i + ", " + (i + 1) + " ]");// test
			}
			else{
				System.out.print("[ " + i + ", " + "villeDepart ]");// test
			}
		}// test
		System.out.println();// test
	}

	
	// public LinkedList<Integer> solve(Double[][] couts, LinkedList<Integer>
	// cheminsInitial){
	public LinkedList<Integer> solve(LinkedList<Integer> cheminsInitial) {

		LinkedList<Integer> cheminsChange = new LinkedList<Integer>();

		if (neighborhood < 2) {
			System.err
					.println("error : nombre de neighborhood est inferieur de 2");
		}

		if (neighborhood == 2) {
			
			shake(cheminsInitial.size());
			for (int i = 0; i <= shakeList.first(); i++) {
				cheminsChange.add(cheminsInitial.get(i));
			}
			for (int i = shakeList.last(); i > shakeList.first(); i--) {
				cheminsChange.add(cheminsInitial.get(i));
			}
			for (int i = shakeList.last() + 1; i < cheminsInitial.size(); i++) {
				cheminsChange.add(cheminsInitial.get(i));
			}
			//cheminsChange.add(cheminsInitial.get(0));// !!! villeArriver =
		
														// villeDepart
		
		}

		if (neighborhood == 3) {

		}

		if (neighborhood > 3) {

		}
		System.out.println("cheminChange : " + cheminsChange);// test
		return cheminsChange;
	}
	
	
	//comparer les couts
	private LinkedList<Integer> neighborhoodChange(Double[][] couts,
			LinkedList<Integer> cheminsInitial,
			LinkedList<Integer> cheminsChange) {
		
		Double coutsTotalInitial = couts[cheminsInitial.get(cheminsInitial
				.size() - 1)][0]; 
		Double coutsTotalChange = couts[cheminsChange.get(cheminsChange
				.size() - 1)][0];
		
		if(couts[cheminsChange.get(cheminsChange.size() - 1)][0] > coutDeCheminExistePas){
				System.out.println("chemin [ " + cheminsChange.get(cheminsChange.size() - 1) + ", " + cheminsChange
			           					.get(0) + "] existe pas");
				return cheminsInitial;//!!!
			}
		
		//cout de cheminInitial
		for (int i = 0; i < cheminsInitial.size() - 1; i++) {
			coutsTotalInitial += couts[cheminsInitial.get(i)][cheminsInitial
					.get(i + 1)];
		}
		System.out.println("coutsTotalInitial : " + coutsTotalInitial);//for test
		
		//cout de cheminChange
		for (int i = 0; i < cheminsChange.size() - 1; i++) {
			if(couts[cheminsChange.get(i)][cheminsChange
			           					.get(i + 1)] > coutDeCheminExistePas){
				System.out.println("chemin [ " + cheminsChange.get(i) + ", " + cheminsChange
			           					.get(i + 1) + "] existe pas");
				return cheminsInitial;//!!!
			}
			coutsTotalChange += couts[cheminsChange.get(i)][cheminsChange
					.get(i + 1)];
		}
		System.out.println("coutsTotalChange : " + coutsTotalChange);//for test
		
		if(coutsTotalChange < coutsTotalInitial){
			System.out.println("on change cheminsInitial a cheminsChange");//for test
			return cheminsChange;
		}
		System.out.println("on change pas de chemin");//for test
		
		return cheminsInitial;
		
	}

	

	public static void main(String[] args) {

		LinkedList<Integer> cheminsInitialTest = new LinkedList<Integer>();
		cheminsInitialTest.add(0);
		cheminsInitialTest.add(1);
		cheminsInitialTest.add(2);
		cheminsInitialTest.add(3);
		cheminsInitialTest.add(4);
		cheminsInitialTest.add(5);
		cheminsInitialTest.add(6);
		
		Double[][] coutsTest = new Double[][] {
				{ 1000.0, 12.0, 10.0, 1000.0, 1000.0, 1000.0, 12.0 },
				{ 12.0, 1000.0, 8.0, 12.0, 1000.0, 1000.0, 1000.0 },
				{ 10.0, 8.0, 1000.0, 11.0, 3.0, 1000.0, 9.0 }, 
				{ 1000.0, 12.0, 11.0, 1000.0, 11.0, 10.0, 1000.0},
				{ 1000.0, 1000.0, 3.0, 11.0, 1000.0, 6.0, 7.0}, 
				{ 1000.0, 1000.0, 1000.0, 10.0, 6.0, 1000.0, 9.0}, 
				{ 12.0, 1000.0, 9.0, 1000.0, 7.0, 9.0, 1000.0}, 
				};

		VNS v = new VNS(2);
		//v.solve(cheminsInitialTest);
		v.neighborhoodChange(coutsTest, cheminsInitialTest, v.solve(cheminsInitialTest));
	}
}


