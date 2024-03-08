package org.example.GUI;

import org.example.Generatory.Ostatne.GeneratorNasad;
import org.example.Simulacia.Strategie.StrategiaA;
import org.example.Simulacia.Strategie.StrategiaB;
import org.example.Simulacia.Strategie.StrategiaC;
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

    private StrategiaB strategiaB;
    private ChartPanel panelStrategiaB;
    private Graf grafStrategiaB;
    private JLabel vysledokStrategiaB;

    private StrategiaC strategiaC;
    private ChartPanel panelStrategiaC;
    private Graf grafStrategiaC;
    private JLabel vysledokStrategiaC;

    public HlavneOkno()
    {
        setTitle("Aplikácia - Nikolaj Cupan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1550, 750);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(this.panel);

        this.strategiaA = new StrategiaA(this.grafStrategiaA, this.vysledokStrategiaA);
        this.strategiaB = new StrategiaB(this.grafStrategiaB, this.vysledokStrategiaB);
        this.strategiaC = new StrategiaC(this.grafStrategiaC, this.vysledokStrategiaC);

        this.buttonSpusti.addActionListener(e -> {
            try
            {
                int pocetReplikacii = Integer.parseInt(this.inputPocetReplikacii.getText());
                boolean nasadaZadana = !this.inputNasada.getText().isEmpty();
                int nasada = (nasadaZadana ? Integer.parseInt(this.inputNasada.getText()) : 0);

                // Za ucelom, aby kazda strategia mala vlastnu nasadu pre svoj vlastny generator nasad,
                // tento generator generuje nasady pre dalsie generatory nasad
                GeneratorNasad generatorNasad = (nasadaZadana ? new GeneratorNasad(nasada) : new GeneratorNasad());

                new Thread(() -> this.strategiaA.simuluj(pocetReplikacii, generatorNasad.nasada(), nasadaZadana)).start();
                new Thread(() -> this.strategiaB.simuluj(pocetReplikacii, generatorNasad.nasada(), nasadaZadana)).start();
                new Thread(() -> this.strategiaC.simuluj(pocetReplikacii, generatorNasad.nasada(), nasadaZadana)).start();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(HlavneOkno.this, "Neplatny pocet replikacii!");
            }
        });

        this.buttonUkonci.addActionListener(e -> {
            this.strategiaA.ukonciSimulaciu();
            this.strategiaB.ukonciSimulaciu();
            this.strategiaC.ukonciSimulaciu();
        });
    }

    public void createUIComponents()
    {
        this.grafStrategiaA = new Graf("Strategia A");
        this.panelStrategiaA = new ChartPanel(this.grafStrategiaA.getGraf());

        this.grafStrategiaB = new Graf("Strategia B");
        this.panelStrategiaB = new ChartPanel(this.grafStrategiaB.getGraf());

        this.grafStrategiaC = new Graf("Strategia C");
        this.panelStrategiaC = new ChartPanel(this.grafStrategiaC.getGraf());
    }
}
