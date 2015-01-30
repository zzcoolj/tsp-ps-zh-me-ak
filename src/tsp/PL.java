package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import mvc.GraphOpt;
import CustomClass.HashLambdaRho;
import CustomClass.PaireLamdbaRho;
import CustomClass.PaireVertex;
import Math.ExceptionMaths;
import Math.Maths;

/**
 * Classe Programme Linéaire
 * 
 * @author Amin Zheng Gishan
 * 
 */
public class PL {

	int n;

	public PL() {

	}

	public GraphOpt solve() {
		return null;

	}

	public Double generateRho() {
		return null;
	}

	public Double generateLambda() {
		return null;
	}

	public void initScenario(ArrayList<Scenario> s, TSP tsp, int n) {
		
		s.clear();
		for(int i = 0; i < n; i++)
		{
			s.add(new Scenario(i));
			s.get(i).getGeneral().setCities(tsp.getG().getVilles());
			s.get(i).getGeneral().setDeterminists(tsp.getG().getDeterminists());
		}
		
		Double ecartype = Maths.calculEcartType(tsp);
		for(PaireVertex p : tsp.getG().getCouts().keySet())
		{
			//PaireVertex p = new PaireVertex(new Vertex(i),new Vertex(j));
			if(tsp.getDeterminists().contains(p) || p.hasSameVertex() || tsp.getDeterminists().contains(p.inverser())){
				for(Scenario scenario : s)
				{
					scenario.getGeneral().getCouts().put(p, tsp.getG().getCouts().get(p));
					scenario.getGeneral().getCouts().put(p.inverser(), tsp.getG().getCouts().get(p));
				}
			}
			else if(p.getFirst().getNumero()<p.getSecond().getNumero())
			{
				Double valeurXml = tsp.getG().getCouts().get(p);	
				////System.out.println("3*ecartype"+(3*ecartype));
				Double min = valeurXml-3*ecartype;
				Double max = valeurXml+3*ecartype;
				try {
					ArrayList<Double> rand = Maths.generateRandomCosts(valeurXml, min, max, s.size());
					//////System.out.println("Rand = "+rand);
					
					int cpt = 0;
					for(Scenario scenario : s)
					{
						scenario.getGeneral().getCouts().put(p, rand.get(cpt));
						scenario.getGeneral().getCouts().put(p.inverser(), rand.get(cpt));
						cpt++;
						
					}
				} catch (ExceptionMaths e) {
					e.printStackTrace();
				}
			}
		}
		
		for(Scenario scenario : s)
		{
			scenario.addObserver(tsp);
		}
		
		
	}

	public Graph generateSolutionReference(Graph g) {

		return null;
	}

	public Graph glouton(Graph g) {
		
		if(g==null)
			return null;

		Vertex depart = g.getVilles().get(Maths.randInt(0, g.getNbVilles()-1));
		ArrayList<PaireVertex> cheminInterdit = new ArrayList<PaireVertex>();
		Vertex vilaInterdit = depart;
		for (int i = 0; i < g.getNbVilles()-1; i++) {
			PaireVertex tmp = null;
			if(i==0) {
				 tmp = procheVoisin(depart, g.getCouts(), cheminInterdit,null);
				 
			}
			else{
				tmp = procheVoisin(depart, g.getCouts(), cheminInterdit, vilaInterdit);
			}
			
			cheminInterdit.add(tmp);
			depart = tmp.getSecond();
		}
		Vertex sortante = cheminInterdit.get(cheminInterdit.size()-1).getSecond();
		Vertex entrante = cheminInterdit.get(0).getFirst(); 
		
		cheminInterdit.add(new PaireVertex(sortante,entrante));
		//////System.out.println(cheminInterdit);
		
		Graph graphe = new Graph(g.getVilles());
		LinkedHashMap<PaireVertex, Double> map = new LinkedHashMap<PaireVertex, Double>();
		for (PaireVertex p : cheminInterdit) {
			map.put(p, g.getCouts().get(p).doubleValue());
		}
		graphe.setCouts(map);

		return graphe;
	}

	
	public PaireVertex procheVoisin(Vertex depart,LinkedHashMap<PaireVertex, Double> map,ArrayList<PaireVertex> cheminInterdit, Vertex interdit) {
		PaireVertex voisin = null;
		double min;
		/**
		 * Correspond au premier coup
		 */
		ArrayList<PaireVertex> liste = new ArrayList<PaireVertex>();
		for (PaireVertex p : map.keySet()) {
			if(interdit == null){
				if (p.getFirst().equals(depart) && !p.getSecond().equals(depart) && !arcExisteDeja(p.getFirst(), p.getSecond(),cheminInterdit)) {
					liste.add(p);
				}
			}
			else{
				if (p.getFirst().equals(depart) && !p.getSecond().equals(depart) && !arcExisteDeja(p.getFirst(), p.getSecond(), cheminInterdit) && !p.getSecond().equals(interdit)) {
					liste.add(p);
				}
			}
		}
		min = Double.MAX_VALUE;
		//////System.out.println("---"+liste+"  ville depart"+depart);
		for (PaireVertex p : liste) {
			if (min > map.get(p).doubleValue()) {
				min = map.get(p).doubleValue();
				voisin = p;
			}
		}
		return voisin;
	}

	public boolean arcExisteDeja(Vertex x, Vertex y, ArrayList<PaireVertex> array) {
		for (PaireVertex vd : array) {
			/*//System.out.println("Je suis a la paire "+vd+" et je cherche si la paire ("+x+","+y+") est autorisé");
			//System.out.println("Ma liste est "+array);
			//System.out.println("vd.getFirst().equals(x)" + vd.getFirst().equals(x));
			//System.out.println("vd.equals(new PaireVertex(y, x))"+vd.equals(new PaireVertex(y, x)));
			//System.out.println("vd.getSecond().equals(y)"+vd.getSecond().equals(y));*/
			if(vd.getFirst().equals(x)){
				return true;
			}
			if (vd.equals(new PaireVertex(y, x))) {
				return true;
			}

			if (vd.getSecond().equals(y)) {
				return true;
			}
			////System.err.println("//////////////////\n");

		}
		return false;
	}
	
	
	public void algoPenalite(int iteration, LinkedHashMap<Integer, ArrayList<HashLambdaRho>> penalite, Graph reference, Graph global, ArrayList<Scenario> listeScenario)
	{
		penalite.put(iteration, new ArrayList<HashLambdaRho>());
		for(Scenario scenario : listeScenario)
		{
			penalite.get(iteration).add(new HashLambdaRho(scenario));
		}
		if(iteration == 0)
		{
			Double M = getMaxValue(reference);
			/**
			 * Pour chaque HashLambdaRho d'un scenario
			 */
			for(int i = 0; i<listeScenario.size(); i++)
			{
				for(PaireVertex paireD : global.getDeterminists())
				{
					////System.out.println("global.... dans algoPenalite "+global.getCouts().get(paireD).doubleValue()/2);
					penalite.get(iteration).get(i).put(paireD, new PaireLamdbaRho(M, global.getCouts().get(paireD).doubleValue()/2));
				}
			}
			
		}
		else
		{
			
			//lambda(i,j) t = lambda(i,j) t-1(s) + rho(i,j) t-1 * ( xij t(s) - xBarre(i,j) t)
			
			//rho(i,j) t = 2*rho(ij) t-1 
			
			for (int i = 0; i < listeScenario.size(); i++) 
			{
				for(PaireVertex paireD : global.getDeterminists())
				{
					int xij = 0;
					if (listeScenario.get(i).getSolution().getCouts().containsKey(paireD)) {
						xij = 1;
					}
					int xijbarre = 0;
					
					if (reference.getCouts().containsKey(paireD)) {
						xijbarre = 1;
					}
					
					if(xij==1){
					Double rhotmoins1 = penalite.get(iteration-1).get(i).get(paireD).getRho();
					Double lambda = penalite.get(iteration-1).get(i).get(paireD).getLambda() + rhotmoins1*Math.abs(xij-xijbarre);
					Double rho = 2.0*rhotmoins1;
					
					penalite.get(iteration).get(i).put(paireD, new PaireLamdbaRho(lambda, rho));
					}
					else if(xij!=1){	

						PaireLamdbaRho p = penalite.get(iteration-1).get(i).get(paireD);
						penalite.get(iteration).get(i).put(paireD,p); 
					}
					
				}
				
			}
			
		}
	}
	
	
	public Double fonctionObjectiveLocalResultat(ArrayList<Scenario> s,LinkedHashMap<Integer, ArrayList<HashLambdaRho>> penalite, Graph reference)
	{
		Double resultat = 0.0;
		for(Scenario scenario : s)
		{
			resultat+=fonctionObjectiveLocaleCalculInterne(scenario, penalite, reference);
		}
		
		/*for(Entry<Integer,ArrayList<HashLambdaRho>> entry : penalite.entrySet())
		{
			////System.err.println("Penalite : iteration "+entry.getKey() + "\n" + entry.getValue()+"\n");
		}*/
		
		return (1f/s.size())*resultat;
	}
	
	private Double fonctionObjectiveLocaleCalculInterne(Scenario s, LinkedHashMap<Integer, ArrayList<HashLambdaRho>> penalite, Graph reference) {
		
		//FIXME => verifier que les aretes deterministe du scenario soient bien init
		
		Double partieDeterminist = 0.0;
		Double partieStochastique = 0.0;
		
		for(Entry<PaireVertex,Double> entry : s.getSolution().getCouts().entrySet())
		{
			////System.err.println("Condition = "+s.getGeneral().getDeterminists().contains(entry.getKey())+" taille "+s.getGeneral().getDeterminists().size());
			//cas deterministe => on verifie que l'on utilise bien les aretes deterministe dans la solution de reference
			int xij = 0;
			if(s.getGeneral().getDeterminists().contains(entry.getKey()))
			{
				xij = 1;
				
				int xijbarre = 0;
				if (reference.getDeterminists().contains(entry.getKey())) {
					xijbarre = 1;
				}
				Double rho = penalite.get(penalite.size()-1).get(s.getNumero()).get(entry.getKey()).getRho();

				if(xij ==1){
					Double calcul = (entry.getValue()+penalite.get(penalite.size()-1).get(s.getNumero()).get(entry.getKey()).getLambda()
							  -(rho*xijbarre)+rho/2f)*xij;
					s.getGeneral().getCouts().replace(entry.getKey(), calcul);	
				}
//				partieDeterminist+=calcul;
			}
			//cas stochastique
			else
			{
				int yij = 0;
				if (s.getSolution().getCouts().containsKey(entry.getKey())) {
					yij = 1;
				}
				partieStochastique+=entry.getValue()*yij;
			}
		}
		//////System.out.println("partieDeterminist+Stochastique = "+(partieDeterminist+partieStochastique));
		return (partieDeterminist+partieStochastique);
	}
	
	public void callVNS(ArrayList<Scenario> s) throws CloneNotSupportedException
	{
		for (Scenario scenario : s) {
			scenario.getVns().findBestSolution(scenario);
		}
	}
	
	public Double getMaxValue(Graph g) {
		Double maxi = 0.0;
		for(Entry<PaireVertex, Double> max : g.getCouts().entrySet()){
			if(!max.getKey().hasSameVertex())			
				{
				if(max.getValue()>maxi){	
					maxi = max.getValue();
			}
		}
		}
		return maxi;
	}
	
	public void initDeterminist(Graph g, float pourcentage)
	{
		
		int nombre = (int)(g.getNbVilles()*(g.getNbVilles()-1)*pourcentage);
		
		int min = 0;
		
		
		ArrayList<PaireVertex> lesPaires = new ArrayList<PaireVertex>();
		lesPaires.addAll(g.getCouts().keySet());
		
		int max = lesPaires.size()-1;
		
		while(g.getDeterminists().size()<nombre)
		{	
			PaireVertex pioche = lesPaires.get(Maths.randInt(min, max));
			if (!g.getDeterminists().contains(pioche) && !pioche.hasSameVertex()) {
				g.getDeterminists().add(pioche);
			}
		}
		
	}
	
	public void gloutonBis(Graph g, ArrayList<PaireVertex> paireReference) {
		
		if(g==null)
			return;

		Vertex depart = trouveVilleInterdit(paireReference);
		//System.out.println("une villes interdit et de depart");
		ArrayList<PaireVertex> cheminInterdit = new ArrayList<PaireVertex>();
		
		for(PaireVertex paire : paireReference)
		{
			cheminInterdit.add(paire);
		}
		
		Vertex vilaInterdit = depart;
		
		ArrayList<Vertex> arreteDejaTrouve = new ArrayList<Vertex>();
		
		for(PaireVertex v : paireReference)
		{
			arreteDejaTrouve.add(v.getFirst());
		}
		
		for (int i = 0; i < g.getNbVilles()-1; i++) {
			if(!arreteDejaTrouve.contains(depart) && !existeDejaDansReference(depart, paireReference))
			{
				PaireVertex tmp = null;
				//System.out.println("Depart : ------ "+depart+" villes interdit "+vilaInterdit);
				if(i==0) 
				{
					 tmp = procheVoisin(depart, g.getCouts(), cheminInterdit,null);
					 //System.out.println("1)Avant nulll je passe la "+tmp);
				}
				else
				{
					tmp = procheVoisin(depart, g.getCouts(), cheminInterdit, vilaInterdit);
					//System.out.println("2)Avant nulll je passe la "+tmp);
				}
				cheminInterdit.add(tmp);
				depart = tmp.getSecond();
			}
		}
		
		//System.out.println("bis : "+cheminInterdit);
		
		Vertex sortante = cheminInterdit.get(cheminInterdit.size()-1).getSecond();
		Vertex entrante = cheminInterdit.get(0).getFirst(); 
		
		cheminInterdit.add(new PaireVertex(sortante,entrante));
		
		
		//System.out.println("chemin interdit"+cheminInterdit);
		
		
	}
	
	private Vertex trouveVilleInterdit(ArrayList<PaireVertex> paireReference) {
		Vertex interdit = null;
		
		for(PaireVertex pv : paireReference)
		{
			if(!aDejaUneAreteCommencantPar(paireReference, pv.getSecond()))
			{
				interdit = pv.getSecond();
				break;
			}
		}
		
		return interdit;
	}

	private boolean aDejaUneAreteCommencantPar(ArrayList<PaireVertex> paireReference, Vertex second) {
		
		for(PaireVertex pv : paireReference)
		{
			if(pv.getFirst().equals(second))
			{
				return true;
			}
		}
		
		return false;
		
	}

	public PaireVertex recursif(ArrayList<PaireVertex> paireReference,PaireVertex paireActuel, int iteration, PaireVertex paireSauvegarde) {
        
        if(paireActuel==null || paireReference==null || paireReference.size()==0)
                return null;
        else
        {
                boolean trouve = false;
                
                for(PaireVertex p : paireReference)
                {
                        if(p.getSecond().equals(paireActuel.getFirst()))
                        {
                                trouve = true;
                                paireActuel = p;
                        }
                }
                
                if(!trouve)
                        return paireActuel;
                
                if(paireActuel.equals(paireSauvegarde) && iteration>0)
                	return paireSauvegarde;
                
                //System.out.println("iteration++ = "+iteration);
                
                return recursif(paireReference, paireActuel,iteration++, paireSauvegarde);
        }
        
        
	}
	
	private boolean existeDejaDansReference(Vertex x, ArrayList<PaireVertex> paireReference)
	{
		for(PaireVertex paire : paireReference)
		{
			if(paire.getFirst().equals(x))
				return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * @param paireReferenceXij 
	 * @throws CloneNotSupportedException 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	public ArrayList<PaireVertex> gloutonBis2(Graph gr, ArrayList<PaireVertex> paireReferenceXij) throws CloneNotSupportedException
    {
            //System.out.println("Debut glouton");
            ////System.out.println("Graphe = \n"+g);
            
            Graph g = (Graph)gr.clone();
            
            g.getDeterminists().clear();
            
            /*g.getDeterminists().add(new PaireVertex(new Vertex(4), new Vertex(12)));
            g.getDeterminists().add(new PaireVertex(new Vertex(5), new Vertex(2)));
            g.getDeterminists().add(new PaireVertex(new Vertex(15), new Vertex(3)));
            g.getDeterminists().add(new PaireVertex(new Vertex(11), new Vertex(16)));
            g.getDeterminists().add(new PaireVertex(new Vertex(16), new Vertex(1)));
            g.getDeterminists().add(new PaireVertex(new Vertex(13), new Vertex(14)));
            //g.getDeterminists().add(new PaireVertex(new Vertex(1), new Vertex(4)));
            g.getDeterminists().add(new PaireVertex(new Vertex(0), new Vertex(9)));*/
            
            for(PaireVertex paire : paireReferenceXij)
            {
            	g.getDeterminists().add(paire);
            	
            }
           
            //System.out.println("G.GetDeterministe "+g.getDeterminists());
            int rand = Maths.randInt(0, gr.getDeterminists().size()-1);
            if(g.getDeterminists().isEmpty())
            {
            	g.getDeterminists().add(gr.getDeterminists().get(rand));
            	rand = 0;
            }
            else
            {
            	rand = Maths.randInt(0, g.getDeterminists().size()-1);
            }
            if(rand<0)
            	rand = 0;
            Vertex sortante = g.getDeterminists().get(rand).getSecond();
            ArrayList<PaireVertex> paireDansGlouton = new ArrayList<PaireVertex>();
            paireDansGlouton.add(g.getDeterminists().get(rand));
            
            Vertex vertexInterditSortant = null;
            
            Vertex copie = null;
            
            PaireVertex last = null;
            try {
                    last = recursif(g.getDeterminists(),(PaireVertex)g.getDeterminists().get(0).clone(),g.getDeterminists().get(0));
            } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
      //      //System.err.println("G.GetDeterministe "+g.getDeterminists());
            if(last!=null)
                    vertexInterditSortant = last.getFirst();
            
      //      //System.err.println("Last = "+last);
            
      //      //System.out.println("J'interdit : "+vertexInterditSortant);

            PaireVertex tmp = cherche(sortante,paireDansGlouton,g,null,vertexInterditSortant);
            paireDansGlouton.add(tmp);
     //       //System.out.println("tmp = "+tmp);
            PaireVertex sauvegarde = null;
       //     //System.out.println("nb villes ="+g.getNbVilles());
            while(paireDansGlouton.size()<g.getNbVilles())
            {
                    if(tmp!=null)
                    {
                            sauvegarde = tmp;
                        //    //System.out.println("hasSameVertex : "+g.getDeterminists().get(0).hasSameVertex());
                            tmp = cherche(tmp.getSecond(),paireDansGlouton,g,g.getDeterminists().get(0).getFirst(),vertexInterditSortant);
                        //    //System.out.println("Sauvegarde"+sauvegarde);
                            if(tmp!=null)
                                    paireDansGlouton.add(tmp);
                            else
                                    break;
                    }
                    
            }
            
        //    //System.out.println("paireDansGlouton.size()<g.getNbVilles() = "+(paireDansGlouton.size()<g.getNbVilles()));
            
            if(sauvegarde!=null)
            {
                    paireDansGlouton.remove(null);
                  //  //System.out.println("Sav = "+sauvegarde);
                    
                    ArrayList<PaireVertex> listeaAjouter = new ArrayList<PaireVertex>();
                    try {
                            recursifList(g.getDeterminists(),(PaireVertex)g.getDeterminists().get(0).clone(), listeaAjouter);
                    } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
                 //   //System.out.println("Liste a ajouter  = "+listeaAjouter);
            
                    
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
                    
            
         //   //System.out.println("Chemin glouton");
            
        //    //System.out.println(paireDansGlouton);

            return paireDansGlouton;
    }
	
	private PaireVertex recursif(ArrayList<PaireVertex> determinists,PaireVertex paireActuel, PaireVertex paireDebut) {
        
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
                if(paireDebut.equals(paireActuel))
                	return paireActuel;
                
                return recursif(determinists, paireActuel,paireDebut);
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
       // //System.out.println("Je cherche plus proche voisin de "+villesSortante);
        for(PaireVertex det : g.getDeterminists())
        {
                //Par exemple on trouve une paire (2,8) bah c'est okay
                if(det.getFirst().equals(villesSortante) && !paireDansGlouton.contains(det) && !det.getSecond().equals(villeInterdit))
                {
                       // //System.out.println("Det : "+det);
                        return det;
                }
                
        }
        PaireVertex plusprocheVoisin = null;

        for(Entry<PaireVertex,Double> entry : g.getCouts().entrySet())
        {
                /*if(entry.getKey().getFirst().equals(new Vertex(100)) && villesSortante.equals(new Vertex(100)))
                {
                        //System.out.println("Je suis au couple = "+entry.getKey());
                        //System.out.println("arcExisteDeja = "+arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), paireDansGlouton));
                        //System.out.println("estdansGlouton = "+paireDansGlouton.contains(entry.getKey()));
                        //System.out.println("first = villesortante = "+entry.getKey().getFirst().equals(villesSortante));
                        //System.out.println("second = villeinterdi 4 = "+entry.getKey().getSecond().equals(villeInterdit));
                }*/
                        
                        if(villeSortantInterdit!=null && !entry.getKey().getSecond().equals(villeSortantInterdit) && !arcExisteDeja(entry.getKey().getFirst(), entry.getKey().getSecond(), paireDansGlouton) && !paireDansGlouton.contains(entry.getKey()) && entry.getKey().getFirst().equals(villesSortante) && villeInterdit!=null && !entry.getKey().getSecond().equals(villeInterdit))
                        {
                                ////System.out.println("Affiche = "+dejaDansDeterministe(entry.getKey().getFirst(), entry.getKey().getSecond(), g.getDeterminists(),paireDansGlouton));
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
                                	////System.err.println("if(!arcExisteDeja(entry.getKey().getSecond(), entry.getKey().getFirst(), paireDansGlouton))"+!arcExisteDeja(entry.getKey().getSecond(), entry.getKey().getFirst(), paireDansGlouton));
                                    ////System.err.println(""+new PaireVertex(entry.getKey().getSecond(), entry.getKey().getFirst())+" paire = "+paireDansGlouton);
                                //	//System.out.println("Villes sortante "+villesSortante);
                                	////System.out.println("Condition = !entry..... "+!entry.getKey().getSecond().equals(villeSortantInterdit));
                                	////System.out.println("villeSortantInterdit : "+villeSortantInterdit+ " entry "+ entry.getKey());
                                    if(!entry.getKey().getSecond().equals(villeSortantInterdit))
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
                                    ////System.out.println("Plus proche voisin "+plusprocheVoisin);
                                }
                        }
                
        }
        ////System.out.println("plus proche voisin"+plusprocheVoisin);
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

}
