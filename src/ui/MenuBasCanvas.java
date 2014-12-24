package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MenuBasCanvas extends JPanel{

	RectangleItem r,open;
	Image solveImage;
	int nbV;
	double cout;
	long time;
	
	public MenuBasCanvas() {
		// TODO Auto-generated constructor stub
		nbV = 0;
		cout = 0;
		time = 0;
		Color c = Color.decode("#27ae60");
		r = new RectangleItem(new Rectangle(new Point(100,10),new Dimension(90, 40)), c,c);
		
		open = new RectangleItem(new Rectangle(new Point(30,5),new Dimension(50, 50)), Color.BLACK,Color.BLACK);
		ImageIcon imgIcon = new ImageIcon("solve.jpg");
		solveImage = imgIcon.getImage();
	}
	
	public void setNbVilles(int nb)
	{
		this.nbV = nb;
		time = 0;
		cout = 0;
		repaint();
		validate();
	}
	
	public void setCout(double d)
	{
		this.cout = d;
		repaint();
		validate();
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/*g.setColor(r.getBackground());
		g.fillRect(r.getPosition().x, r.getPosition().y, r.getR().width, r.getR().height);
		
		g.setColor(r.getBorder());
		g.drawRect(r.getPosition().x, r.getPosition().y, r.getR().width, r.getR().height);*/
		
		/*g.setColor(open.getBackground());
		g.fillRect(open.getPosition().x, open.getPosition().y, open.getR().width, open.getR().height);
		
		g.setColor(open.getBorder());
		g.drawRect(open.getPosition().x, open.getPosition().y, open.getR().width, open.getR().height);
		*/
		
		g.drawImage(solveImage, r.getPosition().x, r.getPosition().y, Color.decode("#27ae60"), this);
		g.drawImage(new ImageIcon("open.png").getImage(), open.getPosition().x, open.getPosition().y, null, this);
		
		g.setColor(Color.decode("#34495e"));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
		if(nbV==0)
		{
			g.drawString("Charger un fichier XML", 600, 35);
		}
		else
		{
			g.drawString("Il y a "+ nbV +" villes", 600, 35);
		}
		
		if(cout==0)
		{
			g.drawString("", 900, 35);
		}
		else
		{
			g.drawString("Cout optimal = "+ cout, 900, 35);
		}
		
		if(time==0)
		{
			g.drawString("", 200, 35);
		}
		else
		{
			g.drawString("Timer = "+ time + " ms" , 200, 35);
		}
	}

	public RectangleItem getR() {
		return r;
	}

	public RectangleItem getOpen() {
		return open;
	}

	public void setTime(long time) {
		// TODO Auto-generated method stub
		this.time = time;
		repaint();
		validate();
	}
	
	
	
}
