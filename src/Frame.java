import java.awt.Container;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	ParseTab parsetab;

	
	public Frame(String titre, int width, int height){
		super(titre);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width,height);
		
		
		parsetab = new ParseTab();
		
		
		this.add(parsetab);
		this.setVisible(true);

	}
	
	
}
