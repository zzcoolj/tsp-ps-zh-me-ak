package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControllerSwing implements ActionListener, ChangeListener{
	JSlider slider;
	JTextField kmaxT, nbScenarioT;
	JLabel label;
	
	public ControllerSwing(JSlider slider, JLabel label, JTextField kmaxT, JTextField nbScenarioT) {
		// TODO Auto-generated constructor stub
		this.slider = slider;
		this.kmaxT = kmaxT;
		this.nbScenarioT = nbScenarioT;
		this.label = label;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(kmaxT))
		{
			System.out.println("kmax :"+kmaxT.getText());
		}
		else if(e.getSource().equals(nbScenarioT))
		{
			System.out.println("nbScenario :"+nbScenarioT.getText());
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(slider))
		{
			label.setText(slider.getValue()+"% deterministes");
		}
	}

}
