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
    private DiskretnyRovnomernyGenerator diskretnyRovnomernyGenerator;
    private SpojityEmpirickyGenerator spojityEmpirickyGenerator;
    private SpojityRovnomernyGenerator spojityRovnomernyGenerator;

    private int pocetVykonanychIteracii;
    private double sucetMesacnychSplatok;
    private double mesacneSplatkySpoluIteracia;

    private final Graf graf;
    private final JLabel labelVysledok;

    public StrategiaA(Graf graf, JLabel labelVysledok)
    {
        this.graf = graf;
        this.labelVysledok = labelVysledok;
    }

    @Override
    protected void predReplikaciami(int nasada, boolean pouziNasadu)
    {
        GeneratorNasad generatorNasad = (pouziNasadu ? new GeneratorNasad(nasada) : new GeneratorNasad());

        this.diskretnyRovnomernyGenerator = new DiskretnyRovnomernyGenerator(1, 4, generatorNasad);
        this.spojityRovnomernyGenerator = new SpojityRovnomernyGenerator(0.9, 2.2, generatorNasad);

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
        this.labelVysledok.setText("Vysledok strategie A: " + this.sucetMesacnychSplatok / this.pocetVykonanychIteracii);
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

        if (this.pocetVykonanychIteracii > (Konstanty.PERCENTO_NEZOBRAZENYCH_REPLIKACII * this.pocetReplikacii)
            && this.pocetVykonanychIteracii % (Konstanty.PERCENTO_KROK_AKTUALIZACIA_GRAFU * this.pocetReplikacii) == 0)
        {
            this.graf.pridajHodnotu(this.pocetVykonanychIteracii, this.sucetMesacnychSplatok / this.pocetVykonanychIteracii);
        }
    }

    @Override
    protected void replikacia()
    {
        double zostavajucaIstina = Konstanty.VYSKA_ISTINY;


        // Fixacia na 5 rokov
        int zostavajuceRoky = Konstanty.POCET_ROKOV;
        int fixovaneObdobie = 5;

        double rocnaUrokovaSadzba = this.diskretnyRovnomernyGenerator.sample();
        double mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * (fixovaneObdobie * 12);
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 3 roky
        zostavajuceRoky = 5;
        fixovaneObdobie = 3;

        rocnaUrokovaSadzba = this.spojityEmpirickyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * (fixovaneObdobie * 12);
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 1 rok
        zostavajuceRoky = 2;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);


        // Fixacia na 1 rok
        zostavajuceRoky = 1;
        fixovaneObdobie = 1;

        rocnaUrokovaSadzba = this.spojityRovnomernyGenerator.sample();
        mesacnaSplatka = Helper.mesacnaSplatka(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky);
        this.mesacneSplatkySpoluIteracia += mesacnaSplatka * fixovaneObdobie * 12;
        zostavajucaIstina = Helper.zostatokIstiny(zostavajucaIstina, rocnaUrokovaSadzba, zostavajuceRoky, fixovaneObdobie);

        if (Math.abs(zostavajucaIstina) > Konstanty.EPSILON)
        {
            throw new RuntimeException("Zostavajuca istina nie je na konci rovna 0 (epsilon: " + Konstanty.EPSILON + ")!");
        }
    }
}
