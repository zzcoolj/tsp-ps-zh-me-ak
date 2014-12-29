package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import tsp.Scenario;

public class ScenarioCanvas extends JPanel{
	
	ArrayList<ScenarioComponent> listeScenario;
	
	public ScenarioCanvas() {
		// TODO Auto-generated constructor stub
		listeScenario = new ArrayList<ScenarioComponent>();
	}
	
	public ScenarioCanvas(ArrayList<Scenario> scenarios) {
		// TODO Auto-generated constructor stub
		listeScenario = new ArrayList<ScenarioComponent>();
		
		int height = 80;
		int width = 150;
		int x = 0;
		int y = 0;
		
		for(Scenario s:scenarios)
		{
			listeScenario.add(new ScenarioComponent(s, new Point(x, y), width, height, Color.RED, Color.RED));
			y+=height;
		}
	}
	
	public void miseAjour(ArrayList<Scenario> scenarios)
	{
		listeScenario.clear();
		
		int height = 80;
		int width = 150;
		int x = 0;
		int y = 0;
		
		for(Scenario s:scenarios)
		{
			listeScenario.add(new ScenarioComponent(s, new Point(x, y), width, height, Color.RED, Color.RED));
			y+=height;
		}
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	
		
		g.setColor(Color.decode("#34495e"));
		g.setFont(new Font("Arial", Font.BOLD, 18));
		
		int yscenario = 0;
		int yetat = 0;
		int yglobal = 0;
		
		for(ScenarioComponent sc:listeScenario)
		{
			yscenario = yglobal+5+20;
			yetat = 5+yscenario+15;
			
			g.setColor(sc.getR().getBackground());
			g.fillRect(sc.getR().getPosition().x, sc.getR().getPosition().y, sc.getR().getR().width, sc.getR().getR().height);
			
			g.setColor(sc.getR().getBorder());
			g.drawRect(sc.getR().getPosition().x, sc.getR().getPosition().y, sc.getR().getR().width, sc.getR().getR().height);
			
			g.setColor(Color.decode("#ffffff"));
			
			g.drawString(sc.getScenario(), 30, yscenario);
			
			//Si c'est neighborhood : on met bien le texte comme il faut
			if(sc.getEtat().contains("hood"))
			{
				g.drawString("Neighborhood", 20, yetat);
				g.drawString("changing", 20, yetat+19);
			}
			else
			{
				g.drawString(sc.getEtat(), 30, yetat);
			}
			
			yglobal += 80;
			
		}
		
	}

}
