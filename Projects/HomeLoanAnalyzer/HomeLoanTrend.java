import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Map.Entry;

import org.jfree.ui.RectangleInsets;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Displays a chart summarising the home loan trend across the loan lifecycle.
 * The chart includes outstanding balance, interest, principal, savings, and
 * payment information.
 */
public class HomeLoanTrend extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BROWN = new Color(102, 51, 0);

    public HomeLoanTrend(String applicationTitle, String chartTitle) {
        super(applicationTitle);

        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Month No.",
                "Amount",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        xyLineChart.setBackgroundPaint(new Color(245, 247, 250));
        xyLineChart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 15));

        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560 * 2, 367));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        chartPanel.setMouseWheelEnabled(true);

        XYPlot plot = xyLineChart.getXYPlot();
        plot.setBackgroundPaint(new Color(250, 252, 255));
        plot.setDomainGridlinePaint(new Color(205, 211, 220));
        plot.setRangeGridlinePaint(new Color(205, 211, 220));
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(255, 128, 128));
        renderer.setSeriesPaint(1, new Color(60, 179, 113));
        renderer.setSeriesPaint(2, new Color(220, 60, 60));
        renderer.setSeriesPaint(3, new Color(255, 190, 92));
        renderer.setSeriesPaint(4, BROWN);
        renderer.setSeriesPaint(5, new Color(65, 105, 225));
        renderer.setSeriesPaint(6, new Color(40, 40, 40));

        renderer.setSeriesStroke(0, new BasicStroke(2.2f));
        renderer.setSeriesStroke(1, new BasicStroke(2.2f));
        renderer.setSeriesStroke(2, new BasicStroke(2.2f));
        renderer.setSeriesStroke(3, new BasicStroke(2.2f));
        renderer.setSeriesStroke(4, new BasicStroke(3.0f));
        renderer.setSeriesStroke(5, new BasicStroke(3.0f));
        renderer.setSeriesStroke(6, new BasicStroke(2.0f));

        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesShapesVisible(3, false);
        renderer.setSeriesShapesVisible(4, false);
        renderer.setSeriesShapesVisible(5, false);
        renderer.setSeriesShapesVisible(6, false);

        plot.setRenderer(renderer);
        setContentPane(chartPanel);

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(3));
        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 11));
        xAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 11));
        yAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
    }

    /**
     * Adds the values from a map into an XY series.
     *
     * @param series the target series
     * @param dataPoints supplier of key/value chart data
     */
    private void addDataPoints(XYSeries series, java.util.Map<Integer, Double> dataPoints) {
        for (Entry<Integer, Double> entry : dataPoints.entrySet()) {
            series.add(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Creates the dataset used to render the chart.
     *
     * @return dataset containing the key loan metrics
     */
    private XYDataset createDataset() {
        final XYSeries amountOutstanding = new XYSeries("1/100th Outstanding");
        addDataPoints(amountOutstanding, LoanCalculator.getAmountOutstandingDataPoints());

        final XYSeries principalPart = new XYSeries("Principal Part");
        addDataPoints(principalPart, LoanCalculator.getPrincipalPartDataPoints());

        final XYSeries interestPart = new XYSeries("Interest Part");
        addDataPoints(interestPart, LoanCalculator.getInterestPartDataPoints());

        final XYSeries amountOutstandingWoTopUp = new XYSeries("1/100th Outstanding W/O TopUp");
        addDataPoints(amountOutstandingWoTopUp, LoanCalculator.getAmountOutstandingWoTopUpDataPoints());

        final XYSeries savings = new XYSeries("1/100th Savings");
        addDataPoints(savings, LoanCalculator.getSavingsDataPoints());

        final XYSeries currentMonthNumber = new XYSeries("Current Month");
        for (int i = 0; i <= 49000; i++) {
            currentMonthNumber.add(LoanCalculator.getCurrentMonthNumber(), i);
        }

        final XYSeries payments = new XYSeries("1/100th Payments");
        addDataPoints(payments, LoanCalculator.getPaymentsDataPoints());

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(amountOutstanding);
        dataset.addSeries(principalPart);
        dataset.addSeries(interestPart);
        dataset.addSeries(amountOutstandingWoTopUp);
        dataset.addSeries(savings);
        dataset.addSeries(currentMonthNumber);
        dataset.addSeries(payments);
        return dataset;
    }
}