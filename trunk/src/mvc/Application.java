package mvc;

import java.util.Scanner;

import tsp.TSP;

public class Application {

	
	public Application() {
		// TODO Auto-generated constructor stub
		
		begin();
	}
	
	private void begin()
	{
		Model model = new Model();
		Vue vue = new Vue();
		model.addObserver(vue);
		model.launch();
		Controleur controleur = new Controleur(model, vue);
		vue.addControler(controleur);
	}
	
	public static void main(String[] args) {
		
		Application app = new Application();
		/*
		 * 
		 * Dé-commenter pour utiliser le programme terminal
		 * 
		 */
		/*String message = "Tapez : \n1 - Pour lancer la version terminal \n2 - Pour lancer la version graphique\n";
		
		String stdin = lireStdin(message);
		while(!stdin.equals("1") && !stdin.equals("2"))
		{
			stdin = lireStdin(message);
		}
		
		if(stdin.equals("1"))
		{
			message = "Saisir le nom du fichier xml : ";
			stdin = lireStdin(message);
			if(!stdin.endsWith(".xml"))
			{
				stdin = stdin+".xml";
			}
			TSP tsp = new TSP("data/"+stdin);
			tsp.launch();
		}
		else
		{
			System.out.println("Lancement de l'interface graphique ...");
			Application app = new Application();
		}*/

	}
	
	public static String lireStdin(String message)
	{
		System.out.print(message);
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		return choice;
	}
	
}
