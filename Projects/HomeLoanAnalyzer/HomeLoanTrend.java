import java.awt.Color;
import java.util.Map.Entry;
import java.awt.BasicStroke; 

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class HomeLoanTrend extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

public HomeLoanTrend( String applicationTitle, String chartTitle ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Month No." ,
         "Amount" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560*2 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      final Color BROWN = new Color(102,51,0);
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.PINK );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.RED );
      renderer.setSeriesPaint( 3 , Color.YELLOW );
      renderer.setSeriesPaint( 4 , BROWN );
      renderer.setSeriesPaint( 5 , Color.BLUE );
      renderer.setSeriesPaint( 6 , Color.BLACK );
      renderer.setSeriesStroke( 0 , new BasicStroke( 2.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      renderer.setSeriesStroke( 3 , new BasicStroke( 2.0f ) );
      renderer.setSeriesStroke( 4 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 5 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 6 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
      
      
      NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();  
      xAxis.setTickUnit(new NumberTickUnit(3));
   }
   
   private XYDataset createDataset( ) {
     final XYSeries amountOutStanding = new XYSeries( "1/100th Outstanding" ); 
      for( Entry<Integer, Double> entry: LoanCalculator.getAmountOutstandingDataPoints().entrySet())
  		{
    	  amountOutStanding.add(entry.getKey(), entry.getValue());
  		}        
      
      final XYSeries principalPart = new XYSeries( "Principal Part" );          
      for( Entry<Integer, Double> entry: LoanCalculator.getPrincipalPartDataPoints().entrySet())
  		{
    	  principalPart.add(entry.getKey(), entry.getValue());
  		}
      
      final XYSeries interestPart = new XYSeries( "Interest Part" );          
      for( Entry<Integer, Double> entry: LoanCalculator.getInterestPartDataPoints().entrySet())
  		{
    	  interestPart.add(entry.getKey(), entry.getValue());
  		}
      
      final XYSeries amountOutStandingWoTopUp = new XYSeries( "1/100th Outstanding W/O TopUp" ); 
      for( Entry<Integer, Double> entry: LoanCalculator.getAmountOutstandingWoTopUpDataPoints().entrySet())
  		{
    	  amountOutStandingWoTopUp.add(entry.getKey(), entry.getValue());
  		} 
      
      final XYSeries savings = new XYSeries( "1/100th Savings" ); 
      for( Entry<Integer, Double> entry: LoanCalculator.getSavingsDataPoints().entrySet())
  		{
    	  savings.add(entry.getKey(), entry.getValue());
  		} 
      
      final XYSeries currentMonthNumber = new XYSeries( "Current Month" );  
      for( int i=0;i<=49000;i++)
      {
    	  currentMonthNumber.add(LoanCalculator.getCurrentMonthNumber(),i); 
      
      }
      
      final XYSeries payments = new XYSeries( "1/100th Payments" );  
      for( Entry<Integer, Double> entry: LoanCalculator.getPaymentsDataPoints().entrySet())
		{
  	  payments.add(entry.getKey(), entry.getValue());
		} 
      
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( amountOutStanding ); //PINK         
      dataset.addSeries( principalPart );  //GREEN        
      dataset.addSeries( interestPart ); //RED
      dataset.addSeries(amountOutStandingWoTopUp); // YELLOW
      dataset.addSeries(savings); //BROWN
      dataset.addSeries(currentMonthNumber); //BLUE
      dataset.addSeries(payments); //BLACK
      return dataset;
   }
}