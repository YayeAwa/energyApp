import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;



public class ParseTab extends JPanel {
	

	JTable table;
	
	private List<String> qns;
	private List<String> qnsFixedVal;
	private List<JTextField> qnsValList;
	private JPanel left, mid, right, bottom;
	private int idClicked =0,x;
	private TableColumnModel tcm;
	private ButtonGroup bg;
	private JComboBox fixedValue;
	private JTextField energyMin, energyMax;
	private double minEnergy, maxEnergy;
	private boolean goodRow = true;
	private List<Double> energyTab, energyTabfinal;
	
	
	public ParseTab(){
		
		this.setLayout(new BorderLayout());	
		
		String[] columnNames = {"State",
	            "energy",
	            "degeneracy",
	            "j",
	            "v"};
		
		Object[][] data = {
			    {new Integer(1), new Double(2143.55), new Integer(1), new Integer(0), new Integer(1)},
			    {new Integer(2), new Double(2147.46), new Integer(3), new Integer(1), new Integer(1)},
			    {new Integer(3), new Double(2154.46), new Integer(5), new Integer(2), new Integer(1)},
			    {new Integer(4), new Double(2166.46), new Integer(7), new Integer(3), new Integer(1)},
			    {new Integer(5), new Double(2181.46), new Integer(9), new Integer(4), new Integer(1)},
			    {new Integer(6), new Double(2200.46), new Integer(11), new Integer(5), new Integer(1)},
			    {new Integer(7), new Double(2223.46), new Integer(13), new Integer(6), new Integer(1)},
			    {new Integer(8), new Double(2249.46), new Integer(15), new Integer(7), new Integer(1)},
			    {new Integer(9), new Double(2280.46), new Integer(17), new Integer(8), new Integer(1)}		    
			};
		
		
		table = new JTable(data, columnNames);
		qns = new ArrayList<String>();
		
		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		
		mid = new JPanel();
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		
		right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		
		qnsValList = new ArrayList<JTextField>();
		qnsFixedVal = new ArrayList<String>();
		energyTab = new ArrayList<Double>();
		energyTabfinal = new ArrayList<Double>();
		
		String[] fixVal;
		
		tcm = table.getTableHeader().getColumnModel();
		 
		//Putting the quantum numbers in a List<String>
		for(x = 3; x < tcm.getColumnCount(); x++)  
		{  
			if(table.getModel().getValueAt(0, x)!=null){
				TableColumn tc = tcm.getColumn(x);  
				qns.add((String)tc.getHeaderValue());
			}
        }
		
		fixVal = new String[qns.size()];
		
		// JComboBox needs a String[] format
		for(int i =0; i<qns.size();i++){
			fixVal[i]=qns.get(i);
		}
				
		
		JLabel selctQn = new JLabel("Select Qn :");
		selctQn.setAlignmentX(CENTER_ALIGNMENT);
		mid.add(selctQn);
		
		
		fixedValue = new JComboBox(fixVal);
		fixedValue.setPreferredSize(new Dimension(100,20));
		fixedValue.setMaximumSize(fixedValue.getPreferredSize());
		fixedValue.setAlignmentX(CENTER_ALIGNMENT);
		fixedValue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Getting the selected Qn.
				idClicked = fixedValue.getSelectedIndex();
				System.out.println(qns.get(idClicked));
			}
		});
		
		mid.add(fixedValue);
		
		
		
		JLabel enterqns = new JLabel("Enter your Qns values :");
		right.add(enterqns);
		
		//Alignment for right column
		right.add(Box.createRigidArea(new Dimension(1,20)));
		
		for(x = 0; x < qns.size(); x++){
			JTextField qnsVal = new JTextField();
			qnsVal.setPreferredSize(new Dimension(200,20));	
			qnsVal.setMaximumSize(qnsVal.getPreferredSize());
			qnsVal.setText("default");
			qnsValList.add(qnsVal);
			JLabel qnsLabel = new JLabel(qns.get(x)+" : ");
			qnsLabel.setAlignmentX(RIGHT_ALIGNMENT);
			mid.add(qnsLabel);
			right.add(qnsVal);
		}

		JLabel min = new JLabel("Minimum energy level :");
		JLabel max = new JLabel("Maximum energy level :");
		
		
		energyMin = new JTextField();
		energyMin.setPreferredSize(new Dimension(200,20));
		energyMin.setMaximumSize(energyMin.getPreferredSize());
		
		energyMax = new JTextField();
		energyMax.setPreferredSize(new Dimension(200,20));
		energyMax.setMaximumSize(energyMin.getPreferredSize());
		
		left.add(min);
		left.add(energyMin);
		left.add(max);
		left.add(energyMax);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(0,3));
		
		center.add(left);
		center.add(mid);
		center.add(right);
		
		
		
		JButton clear = new JButton("Clear fields");
		clear.setPreferredSize(new Dimension(200,20));
		clear.setMaximumSize(clear.getPreferredSize());
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearFields();
			}
		});
		
		JButton valid = new JButton("Validate");
		valid.setPreferredSize(new Dimension(200,20));
		valid.setMaximumSize(valid.getPreferredSize());
		valid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				qnsValList.get(fixedValue.getSelectedIndex()).setText("default");
				getParam();
				getValTab();
				drawGraph dGraph = new drawGraph(energyTabfinal, minEnergy, maxEnergy);
			}
		});
		
		bottom.add(clear, BorderLayout.WEST);
		bottom.add(valid, BorderLayout.EAST);
		
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		
	}
	
	
	
	
	public void getValTab(){
		
		System.out.println("test1");
		energyTab.clear();
		energyTabfinal.clear();
		
		// taking energies according to Qn values.
		for(int i=0; i<table.getRowCount();i++){
			System.out.println(goodRow);
			for(int j=0; j<qnsFixedVal.size();j++){
								
				Object valueofqn = table.getModel().getValueAt(i,j+3);
				Object qnsFixedValCast = "default";
				
				if(!qnsFixedVal.get(j).equals("default")){
					if(valueofqn instanceof String){
						qnsFixedValCast = (String) qnsFixedVal.get(j);	
					}else if(valueofqn instanceof Double){
						qnsFixedValCast = Double.parseDouble(qnsFixedVal.get(j));	
					}else if(valueofqn instanceof Integer){
						qnsFixedValCast = Integer.valueOf(qnsFixedVal.get(j));	
					}
				}
				if(qnsFixedValCast.equals(valueofqn)||qnsFixedVal.get(j).equals("default")){
					goodRow=true;				
					System.out.println("true :" +qnsValList.get(j).getText()+"  "+table.getModel().getValueAt(i,j+3));
				}else{
					goodRow=false;
					System.out.println("false : "+qnsValList.get(j).getText()+"  "+table.getModel().getValueAt(i,j+3));
					break;
				}				
			}
			
			
			
			
			if(goodRow==true){
				energyTab.add((Double)table.getModel().getValueAt(i, 1));
			}
		}
		
		// taking energies between min and max
		for(int k=0; k<energyTab.size();k++){
			if(energyTab.get(k)>minEnergy & energyTab.get(k)<maxEnergy){
				energyTabfinal.add(energyTab.get(k));
				System.out.println(minEnergy);
				System.out.println(maxEnergy);
			}
		}
	}
	
	
	//Getting the qns values
	public void getParam(){
		System.out.println("test2");
		qnsFixedVal.clear();
		//fixed values
		for(int i=0; i<qns.size();i++){
			qnsFixedVal.add(qnsValList.get(i).getText());
			//System.out.println(qnsFixedVal.get(i));
		}
		

		try{
		//Energy
			minEnergy =   Double.parseDouble(energyMin.getText());
		}
		catch(Exception e){
			minEnergy = (double) table.getModel().getValueAt(0, 1);
			//JOptionPane.showMessageDialog(this, "Enter integers for min and max energies");
		}
		try{
		//Energy
			maxEnergy =   Double.parseDouble(energyMax.getText());
		}
		catch(Exception e){
			maxEnergy = (double) table.getModel().getValueAt(table.getModel().getRowCount()-1, 1);
			//JOptionPane.showMessageDialog(this, "Enter integers for min and max energies");
		}
	}
	
	public void clearFields(){
		for(int i=0; i<qns.size();i++){
			qnsValList.get(i).setText("default");
		}
		energyMin.setText(null);
		energyMax.setText(null);
	}
	
	
	
	
}
