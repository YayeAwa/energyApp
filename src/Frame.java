import java.awt.Container;

import javax.swing.JFrame;

public class Frame extends JFrame {
	
	protected Container contentPane;
	ParseTab parsetab;
	static Frame instance;

	
	public Frame(String titre, int width, int height){
		super(titre);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width,height);
		
		instance = this;
		contentPane = getContentPane();
		
		
		parsetab = new ParseTab();
		
		
		this.add(parsetab);
		this.setVisible(true);

	}
	
	
	public static Frame GetInstance(){
		return instance;
	}
	
}
