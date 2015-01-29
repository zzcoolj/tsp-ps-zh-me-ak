package mvc;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ui.MenuBasCanvas;

public class Controleur implements MouseListener{

	Model model;
	Vue vue;
	MenuBasCanvas menu;
	
	public Controleur(Model m, Vue v) {
		// TODO Auto-generated constructor stub
		this.model = m;
		this.vue = v;
		menu = this.vue.getMenuBas();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int decalageScreen = (vue.getWidth()-menu.getWidth());
		//Point clicked = new Point(e.getPoint().x, e.getPoint().y-decalageScreen);
		if(menu.getR().getR().contains(new Point(e.getX() - decalageScreen, e.getY())))
		{
			//System.out.println("Appuie sur open solve => appel de model.getTSP().getPL().solve()");
			
			if(verification())
			{
				if(model.getTsp()!=null)
				{
					model.getTsp().addObserver(vue);
				}
				model.tspSolve((vue.sliderDeterminist.getValue()/100f), Integer.valueOf(vue.kmaxTxtField.getText()), Integer.valueOf(vue.nbscenarioTxtField.getText()));
			}
			else
				lanceAlerte();
			
		}
		else if(menu.getOpen().getR().contains(new Point(e.getX() - decalageScreen, e.getY())))
		{
			//System.out.println("Appui sur filechooser ==> initialisation du PL et dessiner le graphe");
			
			JFileChooser dialogue = new JFileChooser(new File("./data"));
			PrintWriter sortie;
			File fichier;
			
			if (dialogue.showOpenDialog(null)== 
			    JFileChooser.APPROVE_OPTION) {
			    fichier = dialogue.getSelectedFile();
			    
			    if(fichier!=null)
			    {
			    	System.out.println("Open de : "+fichier.getName());
			    	//System.out.println("plo");
			    	if(fichier.getName().endsWith(".xml"))
			    	{
			    		System.out.println("Ouverture en cours ... : "+fichier.getAbsolutePath());
			    		model.changeXML(fichier.getAbsolutePath());
			    		System.out.println("NbVilles = "+model.getTsp().getG().getNbVilles());
			    		vue.bas.setNbVilles(model.getTsp().getG().getNbVilles());
			    		vue.addJList(model.getTsp().getG().getCouts());
			    		
			    		model.launch();
			    	}
			    	else
			    	{
			    		System.out.println("Le fichier choisi n'est pas sous le bon format !");
			    	}
			    }
			    else
			    {
			    	System.out.println("Vous n'avez pas choisi de fichier");
			    }
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean verification()
	{
		if(vue.sliderDeterminist.getValue()==0 || vue.sliderDeterminist.getValue()==100)
			return false;
		if(vue.kmaxTxtField.getText().isEmpty())
			return false;
		if(!isNumeric(vue.kmaxTxtField.getText()) || Integer.parseInt(vue.kmaxTxtField.getText())<2)
			return false;
		if(vue.nbscenarioTxtField.getText().isEmpty())
			return false;
		if(!isNumeric(vue.nbscenarioTxtField.getText()) || Integer.parseInt(vue.nbscenarioTxtField.getText())<10)
			return false;
		
		return true;
	}
	
	public boolean isNumeric(String input) {
		  try {
		    Integer.parseInt(input);
		    return true;
		  }
		  catch (NumberFormatException e) {
		    // s is not numeric
		    return false;
		  }
		}
	
	public void lanceAlerte()
	{
		String alert = "";
		
		if(vue.sliderDeterminist.getValue()==0 || vue.sliderDeterminist.getValue()==100)
		{
			if(vue.sliderDeterminist.getValue()==0)
			{
				alert+="0% d'arêtes deterministe veuillez choisir entre 1 et 99%\n";
			}
			else
			{
				alert+="Utilisez le programme TSP_PNE pour une résolution optimale\n";
			}
		}
		if(vue.kmaxTxtField.getText().isEmpty())
		{
			alert+="kmax champs vide\n";
		}
		else if(!isNumeric(vue.kmaxTxtField.getText()) || Integer.parseInt(vue.kmaxTxtField.getText())<2)
		{
			alert+="Veuillez saisir un kmax correct (nombre >= 2)\n";
		}
		
		if(vue.nbscenarioTxtField.getText().isEmpty())
		{
			alert+="Nb scénario champs vide\n";
		}
		else if(!isNumeric(vue.nbscenarioTxtField.getText()) || Integer.parseInt(vue.nbscenarioTxtField.getText())<10)
		{
			alert+="Veuillez saisir un nombre de scénarios correct (nombre >= 10)\n";
		}
		
		JOptionPane.showMessageDialog(vue,
			    alert,
			    "Parametre(s) incomplet(s)/incorrect(s)",
			    JOptionPane.WARNING_MESSAGE);
	}

}
