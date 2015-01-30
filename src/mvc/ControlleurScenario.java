package mvc;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import tsp.TSP;
import ui.InterfaceGraphique;
import ui.MenuBasCanvas;
import ui.ScenarioCanvas;
import ui.ScenarioComponent;

public class ControlleurScenario implements MouseListener{
	
	private Vue vue;
	private ScenarioCanvas canvas;
	private InterfaceGraphique grapheInterface;
	private Controleur c = null;
	public ControlleurScenario(ScenarioCanvas canvas, InterfaceGraphique grapheInterface,Vue vue) {
		this.vue = vue;
		this.canvas = canvas;
		canvas.addMouseListener(this);
		this.grapheInterface = grapheInterface;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getClickCount()==2)
		{
			int i = 0;
			for(ScenarioComponent component : canvas.getScenarios())
			{
				if(component.getR().r.contains(new Point(e.getX(), e.getY())))
				{
					if(c!=null)
					{
						//System.out.println("Component : "+component.getS().getSolution());
						//System.out.println("Clique sur le scenario "+i);
						if(!c.model.getTsp().getS().isEmpty())
						{
							GraphOpt gr = new GraphOpt();
							gr.setCheminVNS(c.model.getTsp().getS().get(i).getSolution().getCouts());
							double cout = c.model.getTsp().getS().get(i).getSolution().coutSolution();
							gr.setCout(cout);
							grapheInterface.showSoulutionOptimale(gr.getChemin());
							vue.bas.setCout(cout);
						}
					}
					break;
				}
				i++;
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


	public void setC(Controleur c) {
		this.c  = c;
		
	}

}
