package org.example.Simulacia;

import org.example.GUI.Graf;
import org.example.Generatory.DiskretnyRovnomernyGenerator;
import org.example.Generatory.Ostatne.GeneratorNasad;
import org.example.Generatory.Ostatne.SkupinaEmpirickyGenerator;
import org.example.Generatory.SpojityEmpirickyGenerator;
import org.example.Generatory.SpojityRovnomernyGenerator;
import org.example.Ostatne.Konstanty;

import javax.swing.*;

public class StrategiaA extends SimulacneJadro
{
    private GeneratorNasad generatorNasad;

    private DiskretnyRovnomernyGenerator diskretnyRovnomernyGenerator;
    private SpojityEmpirickyGenerator spojityEmpirickyGenerator;
    private SpojityRovnomernyGenerator spojityRovnomernyGenerator;

    private int pocetIteracii;
    private double sucetVysledkov;
    private double mesacneSplatkySpolu;

    private Graf graf;
    private JLabel labelVysledok;

    public StrategiaA(Graf graf, JLabel labelVysledok)
    {
        this.graf = graf;
        this.labelVysledok = labelVysledok;
    }

    @Override
    protected void predReplikaciami(int nasada, boolean pouziNasadu)
    {
        this.generatorNasad = (pouziNasadu ? new GeneratorNasad(nasada) : new GeneratorNasad());

        this.diskretnyRovnomernyGenerator = new DiskretnyRovnomernyGenerator(1, 4, this.generatorNasad);
        this.spojityRovnomernyGenerator = new SpojityRovnomernyGenerator(0.9, 2.2, this.generatorNasad);

        SkupinaEmpirickyGenerator[] skupiny = new SkupinaEmpirickyGenerator[] {
            new SkupinaEmpirickyGenerator(0.10, 0.30, 0.10),
            new SkupinaEmpirickyGenerator(0.30, 0.80, 0.35),
            new SkupinaEmpirickyGenerator(0.80, 1.20, 0.20),
            new SkupinaEmpirickyGenerator(1.20, 2.50, 0.15),
            new SkupinaEmpirickyGenerator(2.50, 3.80, 0.15),
            new SkupinaEmpirickyGenerator(3.80, 4.80, 0.05)
        };
        this.spojityEmpirickyGenerator = new SpojityEmpirickyGenerator(skupiny, this.generatorNasad);

        this.pocetIteracii = 0;
        this.sucetVysledkov = 0.0;
    }

    @Override
    protected void poReplikaciach()
    {
        this.labelVysledok.setText("Vysledok strategia A: " + this.sucetVysledkov / this.pocetIteracii);
    }

    @Override
    protected void predReplikaciou()
    {
        this.pocetIteracii++;
        this.mesacneSplatkySpolu = 0.0;
    }

    @Override
    protected void poReplikacii()
    {
        this.sucetVysledkov += this.mesacneSplatkySpolu;

        if (this.pocetIteracii > 500000 && this.pocetIteracii % 10000 == 0)
        {
            this.graf.pridajHodnotu(this.pocetIteracii, this.sucetVysledkov / this.pocetIteracii);
        }
    }

    @Override
    protected void replikacia()
    {
        this.mesacneSplatkySpolu = 0.0;
        double zostavajucaIstina = Konstanty.VYSKA_ISTINY;


        int zostavajuceRoky = Konstanty.POCET_ROKOV;
        int fixovaneObdobie = 5;

        double rocnaUrokovaSadzba = this.diskretnyRovnomernyGenerator.sample();
        double mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpolu += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        zostavajuceRoky = 5;
        fixovaneObdobie = 3;

        rocnaUrokovaSadzba = this.spojityEmpirickyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpolu += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        zostavajuceRoky = 2;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpolu += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        zostavajuceRoky = 1;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpolu += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);
    }
}
