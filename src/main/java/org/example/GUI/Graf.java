package org.example.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class Graf
{
    private JFreeChart graf;
    private XYSeries dataset;
    private XYSeriesCollection seriesCollection;

    private double max;
    private double min;

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

        this.max = Integer.MIN_VALUE;
        this.min = Integer.MAX_VALUE;
    }

    public void pridajHodnotu(int x, double y)
    {
        if (this.max < y)
        {
            this.max = y;
        }

        if (this.min > y)
        {
            this.min = y;
        }

        this.dataset.add(x, y);
        XYPlot xyPlot = (XYPlot)this.graf.getPlot();
        NumberAxis range = (NumberAxis)xyPlot.getRangeAxis();
        range.setRange(this.min - 1, this.max + 1);
        this.graf.fireChartChanged();
    }

    public JFreeChart getGraf()
    {
        return this.graf;
    }
}
