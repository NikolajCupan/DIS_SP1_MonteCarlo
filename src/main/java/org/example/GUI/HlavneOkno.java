package org.example.GUI;

import org.example.Simulacia.StrategiaA;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HlavneOkno extends JFrame
{
    private JPanel panel;
    private JButton buttonSpusti;
    private JButton buttonUkonci;

    private ChartPanel panelStrategiaA;
    private Graf grafStrategiaA;
    private StrategiaA strategiaA;

    private Thread t1;

    public HlavneOkno()
    {
        setTitle("Aplikácia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1250, 750);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(this.panel);

        this.strategiaA = new StrategiaA(this.grafStrategiaA);

        this.buttonSpusti.addActionListener(e -> {
            new Thread(() -> {
                this.strategiaA.simuluj(100000, 0, false);
            }).start();
        });

        this.buttonUkonci.addActionListener(e -> {
            this.strategiaA.ukonci();
        });
    }

    public void createUIComponents()
    {
        this.grafStrategiaA = new Graf("Strategia A");
        this.panelStrategiaA = new ChartPanel(this.grafStrategiaA.getGraf());
    }
}
