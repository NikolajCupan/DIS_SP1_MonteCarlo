package org.example.GUI;

import org.example.Simulacia.StrategiaA;
import org.jfree.chart.ChartPanel;

import javax.swing.*;

public class HlavneOkno extends JFrame
{
    private JPanel panel;
    private JButton buttonSpusti;
    private JButton buttonUkonci;

    private JTextField inputPocetReplikacii;
    private JTextField inputNasada;

    private StrategiaA strategiaA;
    private ChartPanel panelStrategiaA;
    private Graf grafStrategiaA;
    private JLabel vysledokStrategiaA;

    public HlavneOkno()
    {
        setTitle("Aplikácia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1250, 750);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(this.panel);

        this.strategiaA = new StrategiaA(this.grafStrategiaA, this.vysledokStrategiaA);

        this.buttonSpusti.addActionListener(e -> {
            try
            {
                int pocetReplikacii = Integer.parseInt(this.inputPocetReplikacii.getText());
                boolean nasadaZadana = !this.inputNasada.getText().isEmpty();
                int nasada = (nasadaZadana ? Integer.parseInt(this.inputNasada.getText()) : 0);

                new Thread(() ->
                    this.strategiaA.simuluj(pocetReplikacii, nasada, nasadaZadana)
                ).start();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(HlavneOkno.this, "Neplatny pocet replikacii!");
            }
        });

        this.buttonUkonci.addActionListener(e -> this.strategiaA.ukonciSimulaciu());
    }

    public void createUIComponents()
    {
        this.grafStrategiaA = new Graf("Strategia A");
        this.panelStrategiaA = new ChartPanel(this.grafStrategiaA.getGraf());
    }
}
