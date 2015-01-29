package mvc;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import tsp.TSP;
import ui.InterfaceGraphique;
import ui.ScenarioCanvas;
import ui.ScenarioComponent;

public class ControlleurScenario implements MouseListener{
	
	
	private ScenarioCanvas canvas;
	private InterfaceGraphique grapheInterface;
	public ControlleurScenario(ScenarioCanvas canvas, InterfaceGraphique grapheInterface) {
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
					//System.out.println("Component : "+component.getS().getSolution());
					System.out.println("Clique sur le scenario "+i);
					grapheInterface.draw(component.getS().getSolution());
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

}
