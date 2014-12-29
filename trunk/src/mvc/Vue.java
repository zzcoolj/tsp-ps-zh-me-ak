package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import tsp.Scenario;
import tsp.TSP;
import tsp.Vertex;
import ui.InterfaceGraphique;
import ui.MenuBasCanvas;
import ui.ScenarioCanvas;

public class Vue extends JFrame implements Observer {
	
	JPanel principal,noeud,menu,newmenu;
	ScenarioCanvas scenario;
	InterfaceGraphique grapheInterface;
	MenuBasCanvas bas;
	JList list;//Liste de noeuds
	
	public Vue() {
		// TODO Auto-generated constructor stub
		
		setTitle("TSP");
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setSize(width, height);
		
		init();
		list = new JList();
		
		setLocationRelativeTo(null);
		//setResizable(false); => ne pas toucher pour le moment !
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void init()
	{
		int sizeOfNoeud = 150;
		//Principal
		principal = new JPanel(); principal.setBackground(Color.decode("#2980b9"));
		principal.setLayout(new BorderLayout());
		
		//Graphe
		grapheInterface = new InterfaceGraphique(getWidth()-sizeOfNoeud, getHeight()-180); grapheInterface.setBackground(Color.decode("#ecf0f1"));
		grapheInterface.setPreferredSize(new Dimension(getWidth()-sizeOfNoeud, getHeight()));
		
		//Droite
		JPanel noeudetScenario = new JPanel();
		noeudetScenario.setLayout(new BorderLayout());
		noeudetScenario.setPreferredSize(new Dimension(sizeOfNoeud*2, getHeight()-180));
			//Scenario
		ArrayList<Scenario> s = new ArrayList<Scenario>();
		Scenario tmp = new Scenario(1);
		tmp.setEtat(Scenario.etat.FINISHED);
		Scenario tmp2 = new Scenario(2);
		tmp2.setEtat(Scenario.etat.CHANGINGNEIGHBORHOOD);
		Scenario tmp3 = new Scenario(3);
		tmp3.setEtat(Scenario.etat.WAITING);
		s.add(tmp);
		s.add(tmp2);
		s.add(tmp3);
		
		scenario = new ScenarioCanvas(s);scenario.setBackground(Color.decode("#34495e"));
		scenario.setPreferredSize(new Dimension(sizeOfNoeud, getHeight()-180));
		
			//Noeud
		noeud = new JPanel(); noeud.setBackground(Color.decode("#27ae60"));
		noeud.setPreferredSize(new Dimension(sizeOfNoeud, getHeight()-180));
		noeudetScenario.add(scenario, BorderLayout.CENTER);
		noeudetScenario.add(noeud, BorderLayout.LINE_END);
		
		
		//Menu
		menu = new JPanel(); menu.setBackground(Color.decode("#fb2a3b"));
		menu.setPreferredSize(new Dimension(sizeOfNoeud, 120));
		menu.setLayout(new BorderLayout());
		initMenu();
		menu.add(newmenu,BorderLayout.PAGE_START);
		menu.add(bas,BorderLayout.PAGE_END);
		
		/*JButton solve = new JButton();
		bas.add(solve);
		solve.setText("Solve");
		solve.setBackground(new Color(255,255,255));*/
		
		principal.add(grapheInterface,BorderLayout.CENTER);
		principal.add(noeudetScenario,BorderLayout.LINE_END);
		principal.add(menu,BorderLayout.PAGE_END);
		
		add(principal);
	}
	
	public void initMenu()
	{
		//Bas
		bas = new MenuBasCanvas(); bas.setBackground(Color.decode("#bdc3c7"));
		bas.setPreferredSize(new Dimension(getWidth(), 60));
		bas.repaint();
		
		//New Menu
		newmenu = new JPanel(); newmenu.setBackground(Color.decode("#bdc3c7")); 
		newmenu.setPreferredSize(new Dimension(getWidth(), 60));
		newmenu.setLayout(new GridLayout(1, 3));
		
		JPanel gaucheMenu = new JPanel(); gaucheMenu.setBackground(Color.decode("#bdc3c7")); 
		JSlider sliderDeterminist = new JSlider(0, 100);
		sliderDeterminist.setPreferredSize(new Dimension(320, 20));
		sliderDeterminist.setValue(0);
		//sliderDeterminist.locate(10, 10);
		JLabel labelDeterminist = new JLabel("0% deterministes");
		gaucheMenu.add(sliderDeterminist);
		gaucheMenu.add(labelDeterminist);
		
		JPanel milieuMenu = new JPanel();milieuMenu.setBackground(Color.decode("#bdc3c7")); 
		JLabel labelKmax = new JLabel("kmax");
		JTextField kmaxTxtField = new JTextField(3);
		kmaxTxtField.setBackground(Color.decode("#cccccc"));
		milieuMenu.add(labelKmax);
		milieuMenu.add(kmaxTxtField);
		
		JPanel droitMenu = new JPanel();droitMenu.setBackground(Color.decode("#bdc3c7")); 
		JLabel labelScenario = new JLabel("Nb scenarios");
		JTextField nbscenarioTxtField = new JTextField(3);
		nbscenarioTxtField.setBackground(Color.decode("#cccccc"));
		droitMenu.add(labelScenario);
		droitMenu.add(nbscenarioTxtField);
		
		ControllerSwing swingController = new ControllerSwing(sliderDeterminist, labelDeterminist, kmaxTxtField, nbscenarioTxtField);
		sliderDeterminist.addChangeListener(swingController);
		kmaxTxtField.addActionListener(swingController);
		nbscenarioTxtField.addActionListener(swingController);
		
		newmenu.add(gaucheMenu);
		newmenu.add(milieuMenu);
		newmenu.add(droitMenu);
	}
	
	public void addJList(LinkedHashMap<Vertex, ArrayList<Double>> couts)
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add("Chemins");
		
		int height = 1;
		
		for(Entry<Vertex, ArrayList<Double>> entry : couts.entrySet())
		{
			Vertex key = entry.getKey();
			ArrayList<Double> values = entry.getValue();
			
			int cpt = 0;
			for(Double d : values)
			{
				if(key.getNumero()==cpt)
					data.add(key.getNumero()+" -> " + cpt + " cout = Inf.");
				else
					data.add(key.getNumero()+" -> " + cpt + " cout = " + d);
				cpt++;	
			}
			data.add("--------");
			
			height = height*data.size();
			
			if(key.getNumero()==couts.size()-1)
			{
				data.remove(data.size()-1);
				break;
			}
			
			
		}
		
		//System.out.println("Data = ==== "+data);

		list.setListData(data.toArray());
		//list.validate();
		
		//Dimension dim = new Dimension(noeud.getPreferredSize().width, noeud.getPreferredSize().height*height);
		//list.setPreferredSize(dim);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(noeud.getPreferredSize());
		
		noeud.removeAll();
		noeud.add(scroll);
		noeud.validate();
	}
	
	public void addControler(Controleur c)
	{
		principal.addMouseListener(c);
		grapheInterface.addMouseListener(c);
		noeud.addMouseListener(c);
		bas.addMouseListener(c);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		try {
			if(arg==null)
				return;
			
			if(arg instanceof TSP)
			{
				bas.removeAll();
				//System.out.println("instance of tsp");
				TSP tsp = (TSP)arg;
				if(tsp.getG()!=null && tsp.getG()!=null)
				{
					grapheInterface.draw(tsp.getG());
					grapheInterface.validate();
				}
			}
			else if(arg instanceof GraphOpt)
			{
				//System.out.println("Instance of graphOpt");
				GraphOpt optimal = (GraphOpt)arg;
				
				ArrayList<String> cheminopt = new ArrayList<String>();
				cheminopt.add("Chemin optimal");
				
				for (int i = 0; i < optimal.getChemin().size()-1; i++) {
					cheminopt.add(optimal.getChemin().get(i)+"->"+optimal.getChemin().get(i+1));
				}
				
				list.setListData(cheminopt.toArray());
				bas.setCout(optimal.getCout());
				bas.setTime(optimal.getTime());
				grapheInterface.showSoulutionOptimale(optimal.getChemin());
			}
			
		} catch (ClassCastException e) {
			System.out.println("ClassCast Exception => update()");
		}
		
		//System.out.println("J'ai recu "+arg);
		
	}
	
	public MenuBasCanvas getMenuBas()
	{
		return bas;
	}
}
