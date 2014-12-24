package mvc;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

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
			model.tspSolve();
			
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

}
