package tsp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.TreeSet;
import java.util.Random;

import mvc.GraphOpt;
import tsp.Scenario.etat;
import CustomClass.PaireVertex;
import Math.Maths;

public class VNS extends Observable{
	private int kmax;
	private int neighborhood;
	private TreeSet<Integer> shakeList;// !!!
	private Double coutDeCheminExistePas = Double.MAX_VALUE;
	private Double coutsTotalDeCheminExistePas = Double.MAX_VALUE;

	/* change toTab 
	 * avant		: 	Double[][] couts
	 * 					couts = s.getGeneral().toTab();
	 * 					couts[i][j]
	 * 				
	 * maintenant	: 	LinkedHashMap<PaireVertex, Double> couts
	 * 					couts = s.getGeneral().getCouts();
	 * 					couts.get(new PaireVertex(new Vertex(i), new Vertex(j)))
	*/
	
	
	// HashMap<Vertex, ArrayList<Double>> couts;
	//Double[][] coutsTest;

	public VNS(int neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	public VNS() {
		
	}
	
	private void shake(int nbVille) {
		shakeList = new TreeSet<Integer>();
		Random r = new Random();
		while (shakeList.size() < neighborhood) {
			shakeList.add(r.nextInt(nbVille));// [0, nbVille-1] !!! 若nbville 后面会爆掉
		}
		//for test
		////System.out.print("arretes choisis pour "+neighborhood+"-opt : ");
		/*Iterator<Integer> it = shakeList.iterator();
		System.out.println(shakeList);
		while (it.hasNext()) {
			int i = it.next();
			if(i+1 < nbVille){
				System.out.print("[ " + i + ", " + (i + 1) + " ]");
				
			}
			else{
				System.out.print("[ " + i + ", " + "villeDepart ]");
			}
		}
		System.out.println("\n");*/
	}

	
	// public LinkedList<Integer> solve(Double[][] couts, LinkedList<Integer>
	// cheminsInitial){
	public ArrayList<LinkedList<Integer>> solve(LinkedList<Integer> cheminsInitial) {

		ArrayList<LinkedList<Integer>> listCheminsChange = new ArrayList<LinkedList<Integer>>();

		if (neighborhood < 2) {
			//System.err
					//.println("error : nombre de neighborhood est inferieur de 2");
		}

		if (neighborhood == 2) {
			LinkedList<Integer> cheminsChange = new LinkedList<Integer>();
			
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
			listCheminsChange.add(cheminsChange);
//			//System.out.println("cheminChange : " + cheminsChange);// test
		}

		if (neighborhood == 3) {
			LinkedList<Integer> cheminsChange1 = new LinkedList<Integer>();
			LinkedList<Integer> cheminsChange2 = new LinkedList<Integer>();
			LinkedList<Integer> cheminsChange3 = new LinkedList<Integer>();
			LinkedList<Integer> cheminsChange4 = new LinkedList<Integer>();
			
			shake(cheminsInitial.size());
			int positionFirst = shakeList.first(), positionSecond = shakeList.lower(shakeList.last()), positionThird = shakeList.last();
			/* solution1 */
			for (int i = 0; i <= positionFirst; i++) {
				cheminsChange1.add(cheminsInitial.get(i));
			}
			for (int i = positionSecond; i>=positionFirst+1; i--) {
				cheminsChange1.add(cheminsInitial.get(i));
			}
			for (int i= positionThird; i>= positionSecond+1; i--){
				cheminsChange1.add(cheminsInitial.get(i));
			}
			for (int i= positionThird+1; i< cheminsInitial.size(); i++){
				cheminsChange1.add(cheminsInitial.get(i));
			}
			
			/* solution2 */
			for (int i = 0; i <= positionFirst; i++) {
				cheminsChange2.add(cheminsInitial.get(i));
			}
			for (int i = positionSecond+1; i<=positionThird; i++) {
				cheminsChange2.add(cheminsInitial.get(i));
			}
			for (int i= positionFirst+1; i<= positionSecond; i++){
				cheminsChange2.add(cheminsInitial.get(i));
			}
			for (int i= positionThird+1; i< cheminsInitial.size(); i++){
				cheminsChange2.add(cheminsInitial.get(i));
			}
			
			/* solution3 */
			for (int i = 0; i <= positionFirst; i++) {
				cheminsChange3.add(cheminsInitial.get(i));
			}
			for (int i = positionSecond+1; i<=positionThird; i++) {
				cheminsChange3.add(cheminsInitial.get(i));
			}
			for (int i= positionSecond; i>= positionFirst+1; i--){
				cheminsChange3.add(cheminsInitial.get(i));
			}
			for (int i= positionThird+1; i< cheminsInitial.size(); i++){
				cheminsChange3.add(cheminsInitial.get(i));
			}

			/* solution4 */
			for (int i = 0; i <= positionFirst; i++) {
				cheminsChange4.add(cheminsInitial.get(i));
			}
			for (int i = positionThird; i>=positionSecond+1; i--) {
				cheminsChange4.add(cheminsInitial.get(i));
			}
			for (int i= positionFirst+1; i<= positionSecond; i++){
				cheminsChange4.add(cheminsInitial.get(i));
			}
			for (int i= positionThird+1; i< cheminsInitial.size(); i++){
				cheminsChange4.add(cheminsInitial.get(i));
			}
			
			listCheminsChange.add(cheminsChange1);
			listCheminsChange.add(cheminsChange2);
			listCheminsChange.add(cheminsChange3);
			listCheminsChange.add(cheminsChange4);
			
			/* for test
			//System.out.println("cheminChange1 : " + cheminsChange1);
			//System.out.println("cheminChange2 : " + cheminsChange2);
			//System.out.println("cheminChange3 : " + cheminsChange3);
			//System.out.println("cheminChange4 : " + cheminsChange4);
			*/
		}
		
		
		return listCheminsChange;
	}
	
	
	
	
	
	/* change toTab */
	/*
	// juger change le chemin ou pas	
	private LinkedList<Integer> neighborhoodChange(Double[][] couts, LinkedList<Integer> cheminsInitial) {
		// cout de cheminInitial
		Double coutsTotalInitial = couts[cheminsInitial.get(cheminsInitial
				.size() - 1)][cheminsInitial.get(0)]; 
		for (int i = 0; i < cheminsInitial.size() - 1; i++) {
			coutsTotalInitial += couts[cheminsInitial.get(i)][cheminsInitial
					.get(i + 1)];
		}
		*/
	
	private LinkedList<Integer> neighborhoodChange(LinkedHashMap<PaireVertex, Double> couts, LinkedList<Integer> cheminsInitial) {
		Double coutsTotalInitial = couts.get(new PaireVertex(new Vertex(cheminsInitial.get(cheminsInitial
				.size() - 1)), new Vertex(cheminsInitial.get(0))));
		for (int i = 0; i< cheminsInitial.size() - 1; i++) {
			coutsTotalInitial += couts.get(new PaireVertex(new Vertex(cheminsInitial.get(i)), new Vertex(cheminsInitial
					.get(i + 1))));
		}
		
		
		/*//System.out.println("cheminInitial" + " : " + cheminsInitial + " -----> coutsTotal de cheminInitial" + " : "
				+ coutsTotalInitial);*/ 
		
		/* cout de cheminChange */
		ArrayList<LinkedList<Integer>> listCheminsChange = this
				.solve(cheminsInitial);
		Double coutsTotalChange = coutsTotalDeCheminExistePas;
		int positionCheminsChange = 0;
		

		// parcourt touts les possibilites de cheminsChange
		for (int k = 0; k < listCheminsChange.size(); k++) {
			LinkedList<Integer> cheminsChange = listCheminsChange.get(
					k);
			/* change toTab */
			/*
			Double coutsTotalChangeTemp = couts[cheminsChange.get(cheminsChange
					.size() - 1)][cheminsChange.get(0)];
			if (couts[cheminsChange.get(cheminsChange.size() - 1)][cheminsChange.get(0)] > coutDeCheminExistePas || couts[cheminsChange.get(cheminsChange.size() - 1)][cheminsChange.get(0)] <= 0) {
				coutsTotalChangeTemp = Double.MAX_VALUE;
			*/
			Double coutsTotalChangeTemp = couts.get(new PaireVertex(new Vertex(cheminsChange.get(cheminsChange
					.size() - 1)), new Vertex(cheminsChange.get(0))));
			if (couts.get(new PaireVertex(new Vertex(cheminsChange.get(cheminsChange.size() - 1)), new Vertex(cheminsChange.get(0)))) > coutDeCheminExistePas || couts.get(new PaireVertex(new Vertex(cheminsChange.get(cheminsChange.size() - 1)), new Vertex(cheminsChange.get(0)))) <= 0) {
					coutsTotalChangeTemp = Double.MAX_VALUE;
				
				/*//System.out.println("cheminChange" + (k+1) + " : " + cheminsChange + " -----> coutsTotal de cheminChange" + (k+1) + " : "
						+ coutsTotalChangeTemp);*/
				/*//System.out.println("chemin [ "+ cheminsChange.get(cheminsChange.size() - 1) + ", "+ cheminsChange.get(0) + "] existe pas");*/
				
				continue;
			}

			for (int i = 0; i < cheminsChange.size() - 1; i++) {
				/*
				if (couts[cheminsChange.get(i)][cheminsChange.get(i + 1)] > coutDeCheminExistePas) {
				*/
				
				/* change toTab */
				//if (couts[cheminsChange.get(i)][cheminsChange.get(i + 1)] > coutDeCheminExistePas || couts[cheminsChange.get(i)][cheminsChange.get(i + 1)] <= 0) {
				if (couts.get(new PaireVertex(new Vertex(cheminsChange.get(i)), new Vertex(cheminsChange.get(i + 1)))) > coutDeCheminExistePas || couts.get(new PaireVertex(new Vertex(cheminsChange.get(i)), new Vertex(cheminsChange.get(i + 1)))) <= 0) {
					
					coutsTotalChangeTemp = Double.MAX_VALUE;
					/*//System.out.println("cheminChange" + (k+1) + " : " + cheminsChange + " -----> coutsTotal de cheminChange" + (k+1) + " : "
							+ coutsTotalChangeTemp);// for*/
					// test
					/*//System.out.println("chemin [ " + cheminsChange.get(i)
							+ ", " + cheminsChange.get(i + 1) + "] existe pas");*/
					coutsTotalChangeTemp = Double.MAX_VALUE;
					break;
				}
				
				/* change toTab */
				/*
				if(couts[cheminsChange.get(i)][cheminsChange.get(i + 1)]>0)
				{
					coutsTotalChangeTemp += couts[cheminsChange.get(i)][cheminsChange
					                            						.get(i + 1)];
				}
				*/
				if(couts.get(new PaireVertex(new Vertex(cheminsChange.get(i)), new Vertex(cheminsChange.get(i + 1))))>0)
				{
					coutsTotalChangeTemp += couts.get(new PaireVertex(new Vertex(cheminsChange.get(i)), new Vertex(cheminsChange
    						.get(i + 1))));
				}
				
			}
			if (coutsTotalChangeTemp != Double.MAX_VALUE) {
				/*//System.out.println("cheminChange" + (k+1) + " : " + cheminsChange + " -----> coutsTotal de cheminChange" + (k+1) + " : "
						+ coutsTotalChangeTemp);// for*/
				// test
			}
			if (coutsTotalChangeTemp < coutsTotalChange) {
				coutsTotalChange = coutsTotalChangeTemp;
				positionCheminsChange = k;
			}
		}
		////System.out.println("\n");
		
		
		/* comparer coutsTotalChange et coutsTotalInitial */
		if(coutsTotalChange < coutsTotalInitial){
			////System.out.println("=====> on change cheminsInitial a cheminsChange : " + listCheminsChange.get(positionCheminsChange));//for test
			return listCheminsChange.get(positionCheminsChange);
		}
		////System.out.println("=====> on change pas de chemin");//for test
		
		return cheminsInitial;
		
	}

	

	/*public static void main(String[] args) {

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
		v.neighborhoodChange(coutsTest, cheminsInitialTest, v.solve(cheminsInitialTest));
		
		
	}*/
	
	
	
	/**
	 * |			|
	 * |			|
	 * |			|		ALGO N-OPT
	 * |			|
	 * |			|
	 * @throws CloneNotSupportedException 
	 */
	
	private Graph algoVNSNopt(Scenario s, int kopt) throws CloneNotSupportedException
	{
		
		//FIXME Zheng :))
		if(kopt==2)
		{
			return algoVNS2opt(s);
		}
		else if(kopt==3)
		{
			return algoVNS3opt(s);
		}
		//kopt>3
		else
		{
			
			ArrayList<PaireVertex> stochastiques = new ArrayList<PaireVertex>();
			/**
			 * On ne prend que les arretes stochastiques
			 */
			
			////System.out.println("1) "+s.getSolution()+"\n");
			
			Graph solutionScenarioGloutonClone = (Graph) s.getSolution().clone();
			
			////System.out.println("2) "+solutionScenarioGloutonClone.getCouts());
			////System.out.println("------a------");
			for(PaireVertex paire : solutionScenarioGloutonClone.getCouts().keySet())
			{			
				if(!s.getGeneral().getDeterminists().contains(paire) && !vertexDejaPioche(paire.getFirst(), stochastiques) && !vertexDejaPioche(paire.getSecond(), stochastiques))
					stochastiques.add(paire);
			}
			
			////System.out.println("------b------"+stochastiques.size()+" ===== "+solutionScenarioGloutonClone.getCouts().keySet().size());
			
			/*
			 * --------------------------------------------------
			 * 					SHAKING : Pioche
			 * --------------------------------------------------
			 */
			
			//TODO : cela correspond au shaking ? le fait de piocher au hasard ?
			ArrayList<PaireVertex> pioche = new ArrayList<PaireVertex>();
			/**
			 * Une simple boucle pour dire que l'on doit avoir :
			 * Pour 2-opt : 2 pioche
			 * Pour 3-opt : 3 pioche
			 * etc ...
			 */
			System.out.println("N-opt");
			s.setEtat(etat.SHAKING);
			
			System.out.println("Les stocka : "+stochastiques);
			ArrayList<Integer> randA = new ArrayList<Integer>();
			for(int i = 0;i<stochastiques.size();i++)
			{
				randA.add(i);
			}
			
			int maxintervall = stochastiques.size()-1;
			int erreur = 0;
			while(pioche.size()<kopt && erreur < 20 && maxintervall>0)
			{
				//Random entre [0,stochastiques.size()-1)
				//FIXME : mettre un try catch de java.lang.IllegalArgumentException
				System.out.println("Stocka.size = "+stochastiques.size());
				System.out.println("randA.size = "+randA.size());
				System.out.println("Max = "+maxintervall);
				int positionTab = Maths.randInt(0, maxintervall);
				int rand = randA.get(positionTab);
				
					
					
				PaireVertex choisi = stochastiques.get(rand);
				System.out.println("------b-RANDOM------"+rand+" -> "+choisi);
				if(!pioche.contains(choisi) && !vertexDejaPioche(choisi.getFirst(), pioche) && !vertexDejaPioche(choisi.getSecond(), pioche))
				{
					System.out.println("ajout");
					pioche.add(choisi);
					System.err.println("Nouvelle pioche : "+pioche);
					randA.set(positionTab, randA.get(randA.size()-1));
					randA.remove(randA.size()-1);
					
					randA.add(rand);
					maxintervall--;
					erreur = 0;
				}
				else
				{
					erreur++;
					System.out.println("echec");
				}
			}
			
			System.out.println("Shaking end");
			
			////System.out.println("------c------");
			
			////System.out.println("La pioche : "+pioche);
			
			/*
			 * --------------------------------------------------
			 * 					ALGO : Le saut de pas que l'on fait : CF Image rapport pour N-opt
			 * --------------------------------------------------
			 */
			s.setEtat(etat.CHANGINGNEIGHBORHOOD);

			LinkedHashMap<Vertex, Boolean> visite = new LinkedHashMap<Vertex, Boolean>();
			for(PaireVertex p : pioche)
			{
				visite.put(p.getFirst(), false);
				visite.put(p.getSecond(), false);
			}
			
			ArrayList<PaireVertex> nouveau = new ArrayList<PaireVertex>();
			
			int i = 0;
			while(!toutViste(visite))
			{
				
				if(i+1==pioche.size())
				{
					////System.out.println("2) Je lie : "+pioche.get(i).getFirst()+" avec " + pioche.get(0).getSecond());
					nouveau.add(lie(pioche.get(i).getFirst(), pioche.get(0).getSecond()));
				}
				else
				{
					////System.out.println("3) Je lie : "+pioche.get(i).getFirst()+" avec " + pioche.get(i+1).getSecond());
					nouveau.add(lie(pioche.get(i).getFirst(), pioche.get(i+1).getSecond()));
				}
				////System.out.println("1----");
				PaireVertex tmp = nouveau.get(nouveau.size()-1);
				////System.out.println("2-----");
				visite.put(tmp.getFirst(),true);
				////System.out.println("3-----");
				visite.put(tmp.getSecond(), true);
				////System.out.println("4------");
				////System.out.println("Visite ?? "+visite);
					
				i = i + 1;
			}
				
			//FIXME methode qui colle les nouveau noeud a la solution du scenario : il faut bien les inserer a leur place 
			//genre on doit inserer dans [ (1,2) (4,5) ] l'arrete (2,4) faut la mettre au milieu
			//TODO Cf (FIXME Note 1 au dessus) : on utilisera la meme methode pour cette fois remettre dans l'ordre
			update(solutionScenarioGloutonClone, pioche, nouveau, s);
			
			////System.out.println("Stochastique"+stochastiques);
			////System.out.println("Pioche "+pioche);
			////System.out.println("Nouveau : "+nouveau);
			////System.out.println("Solutionlone : "+solutionScenarioGloutonClone);
			
			//s.setEtat(etat.FINISHED);
			
			return solutionScenarioGloutonClone;
		}
		
		
	}
	
	private Graph algoVNS2ou3opt(Scenario s, boolean is2opt) {
		Graph g = new Graph();
		g.setCities(s.getGeneral().getVilles());
		g.setDeterminists(s.getGeneral().getDeterminists());
		
		/* Couts initial(LinkedHashMap) -> cheminInitial(LinkedList<Integer>) */
		LinkedList<Integer> cheminsInitial = new LinkedList<Integer>();
		Iterator<PaireVertex> it = s.getSolution().getCouts().keySet().iterator();
		while(it.hasNext()){
			cheminsInitial.add(it.next().getFirst().getNumero());
		}
		
		/* utilise la fonction neighborhoodChange pour trouver le chemin optimal(LinkedList<Integer) */
		int opt;
		if(is2opt){
			opt = 2;
		}
		else{
			opt = 3;
		}
		VNS vns = new VNS(opt);
		s.setEtat(etat.CHANGINGNEIGHBORHOOD);
		LinkedList<Integer> cheminsOptimal = new LinkedList<Integer>();
		
		/* change toTab */
		cheminsOptimal = vns.neighborhoodChange(s.getGeneral().getCouts(), cheminsInitial);
		//cheminsOptimal = vns.neighborhoodChange(s.getGeneral().toTab(), cheminsInitial);
		
		//[0,1,2,4,5,3]
		/* cheminOptimal(LinkedList<Integer) ->  Couts optimal(LinkedHashMap) */
		LinkedHashMap<PaireVertex, Double> coutsOptimal = new LinkedHashMap<PaireVertex, Double>();
		for(int i=0;i<cheminsOptimal.size()-1;i++){
			PaireVertex pV = new PaireVertex(new Vertex(cheminsOptimal.get(i)),new Vertex(cheminsOptimal.get(i+1)));
			////System.out.println("Ca "+s.getGeneral().toTab()[cheminsOptimal.get(i)][cheminsOptimal.get(i+1)]+" est egale a ca : "+s.getGeneral().getCouts().get(pV));
			coutsOptimal.put(pV, s.getGeneral().getCouts().get(pV));
		}
		PaireVertex lastPaire = new PaireVertex(new Vertex(cheminsOptimal.get(cheminsOptimal.size()-1)),new Vertex(cheminsOptimal.get(0)));
		coutsOptimal.put(lastPaire, s.getGeneral().getCouts().get(lastPaire));
		
		g.setCouts(coutsOptimal);
		//s.setEtat(etat.FINISHED);
		return g;
	}
	
	//FIXME : lie que si le sommet n'est pas visite !!!!!
	private PaireVertex lie(Vertex x, Vertex y)
	{
		return new PaireVertex(x, y);
	}
	
	private boolean toutViste(LinkedHashMap<Vertex, Boolean> visite)
	{
		for(Boolean b : visite.values())
		{
			if(!b)
				return false;
		}
		return true;
	}
	
	private boolean vertexDejaPioche(Vertex vertex, ArrayList<PaireVertex> pioche)
	{
		for(PaireVertex paire : pioche)
		{
			if(paire.getFirst().equals(vertex) || paire.getSecond().equals(vertex))
				return true;
		}
		return false;
	}
	
	public void update(Graph solution, ArrayList<PaireVertex> pioche, ArrayList<PaireVertex> nouveau, Scenario s){
		
		for(PaireVertex paire : pioche){
			solution.getCouts().remove(paire);
		}
		
		for(PaireVertex paire2 : nouveau){
			solution.getCouts().put(paire2, s.getGeneral().getCouts().get(paire2));
		}
	}
	
	private boolean isBetterSolution(Graph old, Graph actual)
	{
		////System.out.println("Cout old :"+ old.coutSolution());
		////System.out.println("cout actuel :"+ actual.coutSolution());
		Double coutActual = actual.coutSolution();
		if(coutActual<0)
			return false;
		return (old.coutSolution()>actual.coutSolution());
	}

	public void findBestSolution(Scenario s) throws CloneNotSupportedException {
		int kopt = 2;
		
		GraphOpt gopt = new GraphOpt();
		int iteration =0;
		while(kopt <= TSP.n_opt)
		{
			Graph newSolution = algoVNSNopt(s,kopt);
			////System.out.println("solution courante :"+s.getSolution().coutSolution());
			System.out.println("Iteration = "+iteration);
			if(iteration<20){
				if(isBetterSolution(s.getSolution(), newSolution)   )
				{
					s.setSolution(newSolution);
					setChanged();
					//FIXME rendre Paire<newSolution,Scenario s>
					//gopt.setCheminVNS(newSolution.getCouts());
					//gopt.setCout(newSolution.coutSolution());
					//notifyObservers(gopt);
					kopt = 2;
					iteration=0;
				}
			}
			else
			{
				kopt = kopt+1;
				iteration=0;
			}
			iteration++;
		}
		
		s.setEtat(etat.FINISHED);
		
	}
	
	private Graph algoVNS2opt(Scenario s)
	{
		return algoVNS2ou3opt(s, true);
	}
	
	private Graph algoVNS3opt(Scenario s)
	{
		return algoVNS2ou3opt(s, false);
	}
}


