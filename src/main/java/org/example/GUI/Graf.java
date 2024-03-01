package org.example.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graf
{
    private JFreeChart graf;
    private XYSeries dataset;
    private XYSeriesCollection seriesCollection;

    public Graf(String nadpis)
    {
        this.dataset = new XYSeries("Data");
        this.seriesCollection = new XYSeriesCollection(this.dataset);

        this.graf = ChartFactory.createXYLineChart(
            nadpis,
            "Replikacia",
            "Hodnota",
            this.seriesCollection,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
    }

    public void pridajHodnotu(int x, double y)
    {
        this.dataset.add(x, y);
        this.graf.fireChartChanged();
    }

    public JFreeChart getGraf()
    {
        return this.graf;
    }
}
