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
	private Integer kmax;
	private Integer neighborhood;
	private TreeSet<Integer> shakeList;// !!!

	// HashMap<Vertex, ArrayList<Double>> couts;
	Double[][] coutsTest;

	public VNS(int neighborhood) {
		this.neighborhood = neighborhood;
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
			cheminsChange.add(cheminsInitial.get(0));// !!! villeArriver =
														// villeDepart
		}

		if (neighborhood == 3) {

		}

		if (neighborhood > 3) {

		}
		System.out.println("cheminChange : " + cheminsChange);// test
		return cheminsChange;
	}

	private void neighborhoodChange() {
		//comparer les couts
	}

	private void shake(int nbVille) {
		shakeList = new TreeSet<Integer>();
		Random r = new Random();
		while (shakeList.size() < neighborhood) {
			shakeList.add(r.nextInt(nbVille));// [0, nbVille-1] !!! 若nbville 后面会爆掉
			System.out.println("shakeList size : " + shakeList.size()
					+ "\nnombre(s) choisi(s) : " + shakeList.toString());// test
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
		v.solve(cheminsInitialTest);
	}
}
