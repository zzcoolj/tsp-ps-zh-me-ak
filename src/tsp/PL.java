package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import CustomClass.PaireVertex;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;
import mvc.GraphOpt;
/**
 * Classe Programme Linéaire
 * 
 * @author Amin Zheng Gishan
 * 
 */
public class PL {

	int n;

	public PL()
	{
		
	}
	
	public GraphOpt solve()
	{
		return null;
		
	}
	
	public Double generateRho()
	{
		return null;
	}
	
	public Double generateLambda()
	{
		return null;
	}
	
	public void initScenario(ArrayList<Scenario> s, TSP tsp)
	{
		for (Scenario scenario : s) {
			Graph g = new Graph(tsp.getG().getVilles());
			LinkedHashMap<PaireVertex, Double> tmp = new LinkedHashMap<>();
			
			for(int i=0; i<g.getNbVilles(); i++){
				for(int j=0; j<g.getNbVilles();j++){
					PaireVertex p = new PaireVertex(new Vertex(i),new Vertex(j));
					if(tsp.getDeterminists().contains(p) || p.hasSameVertex()){
						tmp.put(p, tsp.getG().getCouts().get(p));
					}
					else{
						Double valeurXml = tsp.getG().getCouts().get(p);
						Double ecartype = Maths.calculEcartType(tsp);
						int nbvaleur = 3;
						
						Double min = valeurXml-ecartype;
						Double max = valeurXml+ecartype;
						try {
							System.out.println(Maths.generateRandomCosts(valeurXml, nbvaleur, min, max));
						} catch (ExceptionMaths e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			g.setCouts(tmp);
			scenario.setSolution(g);
		}
	}
	
	public Graph generateSolutionReference(Graph g)
	{
		
		return null;
	}
	
	public Graph glouton(Graph g)
	{
		System.out.println("Debut glouton");
		//System.out.println("Graphe = \n"+g);
		
		g.getDeterminists().add(new PaireVertex(new Vertex(4), new Vertex(12)));
		g.getDeterminists().add(new PaireVertex(new Vertex(5), new Vertex(2)));
		g.getDeterminists().add(new PaireVertex(new Vertex(15), new Vertex(3)));
		g.getDeterminists().add(new PaireVertex(new Vertex(11), new Vertex(16)));
		g.getDeterminists().add(new PaireVertex(new Vertex(16), new Vertex(1)));
		g.getDeterminists().add(new PaireVertex(new Vertex(13), new Vertex(14)));
		//g.getDeterminists().add(new PaireVertex(new Vertex(1), new Vertex(4)));
		g.getDeterminists().add(new PaireVertex(new Vertex(0), new Vertex(9)));

		
		Vertex sortante = g.getDeterminists().get(0).getSecond();
		ArrayList<PaireVertex> paireDansGlouton = new ArrayList<PaireVertex>();
		paireDansGlouton.add(g.getDeterminists().get(0));
		
		Vertex vertexInterditSortant = null;
		
		Vertex copie = null;
		
		PaireVertex last = null;
		try {
			last = recursif(g.getDeterminists(),g.getDeterminists().get(0).clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(last!=null)
			vertexInterditSortant = last.getFirst();
		
		System.out.println("J'interdit : "+vertexInterditSortant);

		PaireVertex tmp = cherche(sortante,paireDansGlouton,g,null,vertexInterditSortant);
		paireDansGlouton.add(tmp);
		System.out.println("tmp = "+tmp);
		PaireVertex sauvegarde = null;
		System.out.println("nb villes ="+g.getNbVilles());
		while(paireDansGlouton.size()<g.getNbVilles())
		{
			if(tmp!=null)
			{
				sauvegarde = tmp;
				tmp = cherche(tmp.getSecond(),paireDansGlouton,g,g.getDeterminists().get(0).getFirst(),vertexInterditSortant);
				System.out.println("Sauvegarde"+sauvegarde);
				if(tmp!=null)
					paireDansGlouton.add(tmp);
				else
					break;
			}
			
		}
		
		System.out.println("paireDansGlouton.size()<g.getNbVilles() = "+(paireDansGlouton.size()<g.getNbVilles()));
		
		if(sauvegarde!=null)
		{
			paireDansGlouton.remove(null);
			System.out.println("Sav = "+sauvegarde);
			
			ArrayList<PaireVertex> listeaAjouter = new ArrayList<PaireVertex>();
			try {
				recursifList(g.getDeterminists(), g.getDeterminists().get(0).clone(), listeaAjouter);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Liste a ajouter  = "+listeaAjouter);
		
			
			Collections.reverse(listeaAjouter);
			
			if(listeaAjouter.size()>0)
			{
				paireDansGlouton.add(new PaireVertex(sauvegarde.getSecond(), listeaAjouter.get(0).getFirst()));
				for(PaireVertex paire : listeaAjouter)
				{
					paireDansGlouton.add(paire);
				}
				
			}
			
			if(listeaAjouter.size()==0)
				paireDansGlouton.add(new PaireVertex(sauvegarde.getSecond(), g.getDeterminists().get(0).getFirst()));
		}
			
		
		System.out.println("Chemin glouton");
		
		System.out.println(paireDansGlouton);

		return null;
	}
	
	

	private PaireVertex recursif(ArrayList<PaireVertex> determinists,PaireVertex paireActuel) {
		
		if(paireActuel==null || determinists==null || determinists.size()==0)
			return null;
		else
		{
			boolean trouve = false;
			
			for(PaireVertex p : determinists)
			{
				if(p.getSecond().equals(paireActuel.getFirst()))
				{
					trouve = true;
					paireActuel = p;
				}
			}
			
			if(!trouve)
				return paireActuel;
			
			return recursif(determinists, paireActuel);
		}
		
		
	}
	
	private void recursifList(ArrayList<PaireVertex> determinists,PaireVertex paireActuel, ArrayList<PaireVertex> liste) {
		
		if(paireActuel==null || determinists==null || determinists.size()==0)
			return;
		else
		{
			 
			boolean trouve = false;
			
			for(PaireVertex p : determinists)
			{
				if(p.getSecond().equals(paireActuel.getFirst()))
				{
					trouve = true;
					paireActuel = p;
					liste.add(paireActuel);
				}
			}
			
			if(!trouve)
				return;
			
			recursifList(determinists, paireActuel,liste);
		}
		
		
	}

	//Cherche plus proche voisin
	//Supposon villesortantes = 2
	public PaireVertex cherche(Vertex villesSortante,ArrayList<PaireVertex> paireDansGlouton,Graph g, Vertex villeInterdit, Vertex villeSortantInterdit)
	{
		System.out.println("Je cherche plus proche voisin de "+villesSortante);
		for(PaireVertex det : g.getDeterminists())
		{
			//Par exemple on trouve une paire (2,8) bah c'est okay
			if(det.getFirst().equals(villesSortante) && !paireDansGlouton.contains(det) && !det.getSecond().equals(villeInterdit))
			{
				System.out.println("Det : "+det);
				return det;
			}
			
		}
		PaireVertex plusprocheVoisin = null;

		for(Entry<PaireVertex,Double> entry : g.getCouts().entrySet())
		{
			if(entry.getKey().getFirst().equals(new Vertex(100)) && villesSortante.equals(new Vertex(100)))
			{
				System.out.println("Je suis au couple = "+entry.getKey());
				System.out.println("arcExisteDeja = "+arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), paireDansGlouton));
				System.out.println("estdansGlouton = "+paireDansGlouton.contains(entry.getKey()));
				System.out.println("first = villesortante = "+entry.getKey().getFirst().equals(villesSortante));
				System.out.println("second = villeinterdi 4 = "+entry.getKey().getSecond().equals(villeInterdit));
			}
				
				if(villeSortantInterdit!=null && !entry.getKey().getSecond().equals(villeSortantInterdit) && !arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), paireDansGlouton) && !paireDansGlouton.contains(entry.getKey()) && entry.getKey().getFirst().equals(villesSortante) && villeInterdit!=null && !entry.getKey().getSecond().equals(villeInterdit))
				{
					//System.out.println("Affiche = "+dejaDansDeterministe(entry.getKey().getFirst(), entry.getKey().getSecond(), g.getDeterminists(),paireDansGlouton));
					if(!dejaDansDeterministe(entry.getKey().getFirst(), entry.getKey().getSecond(), g.getDeterminists(),paireDansGlouton))
					{
						if(plusprocheVoisin==null)
						{
							plusprocheVoisin = entry.getKey();
						}
						else if(entry.getValue()<g.getCouts().get(plusprocheVoisin))
						{
							plusprocheVoisin = entry.getKey();
						}
					}
				}
				else if(villeInterdit==null && entry.getKey().getFirst().equals(villesSortante))
				{
					if(villeSortantInterdit!=null && !entry.getKey().getFirst().equals(villeSortantInterdit) && !dejaDansDeterministe(entry.getKey().getFirst(), entry.getKey().getSecond(), g.getDeterminists(),paireDansGlouton))
					{
						if(plusprocheVoisin==null)
						{
							plusprocheVoisin = entry.getKey();
						}
						else if(entry.getValue()<g.getCouts().get(plusprocheVoisin))
						{
							plusprocheVoisin = entry.getKey();
						}
					}
				}
			
		}
		System.out.println("plus proche voisin"+plusprocheVoisin);
		return plusprocheVoisin;
	}
	
	private boolean dejaDansDeterministe(Vertex x, Vertex y, ArrayList<PaireVertex> determinist, ArrayList<PaireVertex> arrayGlouton)
	{
		for(PaireVertex vd : determinist)
		{
			if(vd.getFirst().equals(x) || vd.getSecond().equals(y))
			{
				return true;
			}
		}
	
		
		return false;
	}
	
	private boolean arcExisteDeja(Vertex x, Vertex y, ArrayList<PaireVertex> array)
	{
		int i = 0;
		for(PaireVertex vd : array)
		{
			if(vd.equals(new PaireVertex(y, x)))
			{
				return true;
			}
			
			if(vd.getSecond().equals(y))
			{
				return true;
			}
			
			i++;
		}
		
		return false;
	}
	
	private boolean arcExisteDeja(Vertex x, Vertex y, ArrayList<Vertex> departs, ArrayList<Vertex> arrivee)
	{
		int i = 0;
		for(Vertex vd : departs)
		{
			if(vd.equals(y))
			{
				return arrivee.get(i).equals(x);
			}
			
			i++;
		}
		
		return false;
	}
	
	private Map<PaireVertex, Double> sort(List<Map.Entry<PaireVertex, Double>> entries)
	{
		Collections.sort(entries, new Comparator<Map.Entry<PaireVertex, Double>>() {

			@Override
			public int compare(Entry<PaireVertex, Double> o1, Entry<PaireVertex, Double> o2) {
				if(o1.getValue()<=o2.getValue())
					return -1;
				else
					return 1;
			}
			
		});
		
		Map<PaireVertex, Double> sortedMap = new LinkedHashMap<PaireVertex, Double>();
		for (Map.Entry<PaireVertex, Double> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
			//System.out.println("Paire = "+entry.getKey()+" value = "+entry.getValue());
		}
		return sortedMap;
	}

}
