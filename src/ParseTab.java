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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;



public class ParseTab extends JPanel {
	
	String[] columnNames = {"State",
            "energy",
            "degeneracy",
            "j",
            "v"};
	
	Object[][] data = {
		    {new Integer(1), new Float(2143.55), new Integer(1), new Integer(0), new Integer(1)},
		    {new Integer(2), new Float(2147.46), new Integer(3), new Integer(1), new Integer(1)},
		    {new Integer(3), new Float(2154.46), new Integer(5), new Integer(2), new Integer(1)},
		    {new Integer(4), new Float(2166.46), new Integer(7), new Integer(3), new Integer(1)},
		    {new Integer(5), new Float(2181.46), new Integer(9), new Integer(4), new Integer(1)},
		    {new Integer(6), new Float(2200.46), new Integer(11), new Integer(5), new Integer(1)},
		    {new Integer(7), new Float(2223.46), new Integer(13), new Integer(6), new Integer(1)},
		    {new Integer(8), new Float(2249.46), new Integer(15), new Integer(7), new Integer(1)},
		    {new Integer(9), new Float(2280.46), new Integer(17), new Integer(8), new Integer(1)}		    
		};
	
	JTable table;
	
	private List<String> qns;
	private List<Object> qnsFixedVal;
	private List<JFormattedTextField> qnsValList;
	private JPanel left, mid, right, bottom;
	private int idClicked =0,x;
	private TableColumnModel tcm;
	private ButtonGroup bg;
	private JComboBox fixedValue;
	private JFormattedTextField energyMin, energyMax;
	private long minEnergy, maxEnergy;
	private boolean goodRow = true;
	private List<Float> energyTab, energyTabfinal;
	
	
	public ParseTab(){
		
		this.setLayout(new BorderLayout());
		
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
		
		qnsValList = new ArrayList<JFormattedTextField>();
		qnsFixedVal = new ArrayList<Object>();
		energyTab = new ArrayList<Float>();
		energyTabfinal = new ArrayList<Float>();
		
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
		
		//Alignment for right column
		right.add(Box.createRigidArea(new Dimension(1,20)));
		
		JLabel enterqns = new JLabel("Enter your Qns values :");
		right.add(enterqns);
		
		for(x = 0; x < qns.size(); x++){
			JFormattedTextField qnsVal = new JFormattedTextField();
			qnsVal.setPreferredSize(new Dimension(200,20));	
			qnsVal.setMaximumSize(qnsVal.getPreferredSize());
			qnsVal.setValue("default");
			qnsValList.add(qnsVal);
			JLabel qnsLabel = new JLabel(qns.get(x)+" : ");
			qnsLabel.setAlignmentX(CENTER_ALIGNMENT);
			mid.add(qnsLabel);
			right.add(qnsVal);
		}

		JLabel min = new JLabel("Minimum energy level :");
		JLabel max = new JLabel("Maximum energy level :");
		
		
		energyMin = new JFormattedTextField(NumberFormat.getNumberInstance());
		energyMin.setPreferredSize(new Dimension(200,20));
		energyMin.setMaximumSize(energyMin.getPreferredSize());
		
		energyMax = new JFormattedTextField(NumberFormat.getNumberInstance());
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
		
		
		
		JButton valid = new JButton("Validate");
		valid.setPreferredSize(new Dimension(200,20));
		valid.setMaximumSize(valid.getPreferredSize());
		valid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getParam();
			}
		});
		
		
		bottom.add(valid, BorderLayout.EAST);
		
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		
	}
	
	
	
	
	public void getValTab(){
		
		// taking energies according to Qn values.
		for(int i=0; i<table.getRowCount();i++){
			
			for(int j=0; j<qnsFixedVal.size();j++){
				if(qnsValList.get(j).getValue().equals(table.getModel().getValueAt(j+4,i))|qnsValList.get(j).getValue().equals("default")){
					goodRow=true;					
				}else{
					goodRow=false;
					break;
				}			
			}
			
			if(goodRow==true){
				energyTab.add((Float) table.getModel().getValueAt(i, 1));
			}
		}
		
		// taking energies between min and max
		for(int k=0; k<energyTab.size();k++){
			if(energyTab.get(k)>minEnergy & energyTab.get(k)<maxEnergy){
				energyTabfinal.add(energyTab.get(k));
			}
		}
		
		
	}
	
	
	//Getting the qns values
	public void getParam(){
		
		//fixed values
		for(int i=0; i<qns.size();i++){
			qnsFixedVal.add(qnsValList.get(i).getValue());
			System.out.println(qnsFixedVal.get(i));
		}
		
		//Energy
		minEnergy = (long) energyMin.getValue();
		maxEnergy = (long) energyMax.getValue();
		System.out.println(minEnergy);
	}
	
	
	
	
	
}
