package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import tsp.Scenario;

public class ScenarioComponent extends Forme{

	private RectangleItem r;
	private String scenario;
	private String etat;
	
	public ScenarioComponent(Scenario s, Point position, int width, int height,Color background, Color border) {
		// TODO Auto-generated constructor stub
		super(position, background, border);
		scenario = "Scenario "+s.getNumero();
		
		switch (s.getEtat()) {
		case FINISHED:
			etat = "Finished";
			background = Color.decode("#27ae60");
			break;
		case WAITING:
			etat = "Waiting";
			background = Color.decode("#e74c3c");
			break;
		case SHAKING:
			etat = "Shaking";
			background = Color.decode("#e67e22");
			break;
		case CHANGINGNEIGHBORHOOD:
			etat = "Neighborhood Changing";
			background = Color.decode("#e67e22");
			break;
		default:
			break;
		}
		
		r = new RectangleItem(new Rectangle(position,new Dimension(width,height)), background,border);
		
	}

	public RectangleItem getR() {
		return r;
	}

	public String getScenario() {
		return scenario;
	}

	public String getEtat() {
		return etat;
	}
	
	
	
}
