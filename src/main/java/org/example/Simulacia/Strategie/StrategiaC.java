package org.example.Simulacia.Strategie;

import org.example.GUI.Graf;
import org.example.Generatory.DiskretnyRovnomernyGenerator;
import org.example.Generatory.Ostatne.GeneratorNasad;
import org.example.Generatory.Ostatne.SkupinaEmpirickyGenerator;
import org.example.Generatory.SpojityEmpirickyGenerator;
import org.example.Generatory.SpojityRovnomernyGenerator;
import org.example.Ostatne.Konstanty;
import org.example.Simulacia.Helper;
import org.example.Simulacia.SimulacneJadro;

import javax.swing.*;

public class StrategiaC extends SimulacneJadro
{
    private DiskretnyRovnomernyGenerator diskretnyRovnomernyGenerator;
    private SpojityRovnomernyGenerator spojityRovnomernyGenerator01;
    private SpojityEmpirickyGenerator spojityEmpirickyGenerator;
    private SpojityRovnomernyGenerator spojityRovnomernyGenerator02;

    private int pocetVykonanychIteracii;
    private double sucetMesacnychSplatok;
    private double mesacneSplatkySpoluIteracia;

    private final Graf graf;
    private final JLabel labelVysledok;
    private final double vyskaIstiny;

    public StrategiaC(Graf graf, JLabel labelVysledok, double vyskaIstiny)
    {
        this.graf = graf;
        this.labelVysledok = labelVysledok;
        this.vyskaIstiny = vyskaIstiny;
    }

    @Override
    protected void predReplikaciami(int nasada, boolean pouziNasadu)
    {
        GeneratorNasad generatorNasad = (pouziNasadu ? new GeneratorNasad(nasada) : new GeneratorNasad());

        this.diskretnyRovnomernyGenerator = new DiskretnyRovnomernyGenerator(1, 4, generatorNasad);
        this.spojityRovnomernyGenerator01 = new SpojityRovnomernyGenerator(0.3, 5.0, generatorNasad);
        this.spojityRovnomernyGenerator02 = new SpojityRovnomernyGenerator(0.9, 2.2, generatorNasad);

        SkupinaEmpirickyGenerator[] skupiny = new SkupinaEmpirickyGenerator[] {
                new SkupinaEmpirickyGenerator(0.10, 0.30, 0.10),
                new SkupinaEmpirickyGenerator(0.30, 0.80, 0.35),
                new SkupinaEmpirickyGenerator(0.80, 1.20, 0.20),
                new SkupinaEmpirickyGenerator(1.20, 2.50, 0.15),
                new SkupinaEmpirickyGenerator(2.50, 3.80, 0.15),
                new SkupinaEmpirickyGenerator(3.80, 4.80, 0.05)
        };
        this.spojityEmpirickyGenerator = new SpojityEmpirickyGenerator(skupiny, generatorNasad);

        this.pocetVykonanychIteracii = 0;
        this.sucetMesacnychSplatok = 0.0;
        this.mesacneSplatkySpoluIteracia = 0.0;
    }

    @Override
    protected void poReplikaciach()
    {
        this.labelVysledok.setText("Vysledok strategie C: " + this.sucetMesacnychSplatok / this.pocetVykonanychIteracii);
    }

    @Override
    protected void predReplikaciou()
    {
        this.pocetVykonanychIteracii++;
        this.mesacneSplatkySpoluIteracia = 0.0;
    }

    @Override
    protected void poReplikacii()
    {
        this.sucetMesacnychSplatok += this.mesacneSplatkySpoluIteracia;

        int vykreslenyBod = (int)Math.ceil((double)this.pocetReplikacii / Konstanty.POCET_VYKRESLENYCH_BODOV);
        if (this.pocetVykonanychIteracii > (Konstanty.PERCENTO_NEZOBRAZENYCH_REPLIKACII * this.pocetReplikacii)
            && this.pocetVykonanychIteracii % vykreslenyBod == 0)
        {
            this.graf.pridajHodnotu(this.pocetVykonanychIteracii, this.sucetMesacnychSplatok / this.pocetVykonanychIteracii);
            this.labelVysledok.setText("Vysledok strategie B: " + this.sucetMesacnychSplatok / this.pocetVykonanychIteracii);
        }
    }

    @Override
    protected void replikacia()
    {
        double zostavajucaIstina = this.vyskaIstiny;


        // Fixacia na 3 roky
        int zostavajuceRoky = Konstanty.POCET_ROKOV;
        int fixovaneObdobie = 3;

        double rocnaUrokovaSadzba = this.diskretnyRovnomernyGenerator.sample();
        double mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * (fixovaneObdobie * 12);
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 1 rok
        zostavajuceRoky = 7;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator01.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * (fixovaneObdobie * 12);
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 5 rokov
        zostavajuceRoky = 6;
        fixovaneObdobie = 5;

        rocnaUrokovaSadzba = this.spojityEmpirickyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 1 rok
        zostavajuceRoky = 1;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator02.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);

        if (Math.abs(zostavajucaIstina) > Konstanty.EPSILON)
        {
            throw new RuntimeException("Zostavajuca istina nie je na konci rovna 0 (epsilon: " + Konstanty.EPSILON + ")!");
        }
    }
}
