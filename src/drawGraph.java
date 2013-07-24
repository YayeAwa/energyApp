import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;


public class drawGraph extends JFrame {
	
	double[] v1;
	public drawGraph(List<Double> energyList, double energyMin, double energyMax){
		super("Graph");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,600);
		
		
		int bin = 6000; 		  
		HistogramDataset dataset = new HistogramDataset();
		v1 = new double[energyList.size()];
		  for(int i=0; i<energyList.size();i++){
				  v1[i]=(double) energyList.get(i);
				  System.out.println(v1[i]);
		  }
		
	    dataset.addSeries("Energy (1/cm)",v1 , bin, energyMin, energyMax);
	   
	    JFreeChart chart = ChartFactory.createHistogram(
	              "Energy diagram",
	              null,
	              null,
	              dataset,
	              PlotOrientation.HORIZONTAL,
	              true,
	              false,
	              false
	          );

	    chart.setBackgroundPaint(new Color(230,230,230));
	    XYPlot xyplot = (XYPlot)chart.getPlot();
	    xyplot.setForegroundAlpha(0.7F);
	    xyplot.setBackgroundPaint(Color.WHITE);
	    xyplot.setDomainGridlinePaint(new Color(150,150,150));
	    xyplot.setRangeGridlinePaint(new Color(150,150,150));
	    XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
	    xybarrenderer.setShadowVisible(false);
	    xybarrenderer.setBarPainter(new StandardXYBarPainter());
	    
	    
	    ChartPanel cpanel = new ChartPanel(chart);
	    getContentPane().add(cpanel, BorderLayout.CENTER);
	    
	    setVisible(true);
	  }
	 
	

	
	
}
