package org.example;

import org.example.GUI.HlavneOkno;
import org.example.Generatory.Ostatne.GeneratorNasad;
import org.example.Generatory.Ostatne.SkupinaEmpirickyGenerator;
import org.example.Generatory.SpojityEmpirickyGenerator;
import org.example.Testovanie.TestSpojityEmpirickyGenerator;

import java.io.PrintWriter;

public class Main
{
    private static char REZIM = 'T';
    private static String MENO_SUBORU = "test.txt";

    public static void main(String[] args)
    {
        if (Main.REZIM == 'G')
        {
            new HlavneOkno();
        }
        else if (Main.REZIM == 'S')
        {
            GeneratorNasad n = new GeneratorNasad();

            SkupinaEmpirickyGenerator[] skupiny = new SkupinaEmpirickyGenerator[] {
                    new SkupinaEmpirickyGenerator(0.10, 0.30, 0.10),
                    new SkupinaEmpirickyGenerator(0.30, 0.80, 0.35),
                    new SkupinaEmpirickyGenerator(0.80, 1.20, 0.20),
                    new SkupinaEmpirickyGenerator(1.20, 2.50, 0.15),
                    new SkupinaEmpirickyGenerator(2.50, 3.80, 0.15),
                    new SkupinaEmpirickyGenerator(3.80, 4.80, 0.05)
            };
            SpojityEmpirickyGenerator g = new SpojityEmpirickyGenerator(skupiny, n);

            try
            {
                PrintWriter writer = new PrintWriter(Main.MENO_SUBORU);

                for (int i = 0; i < 1000000; i++)
                {
                    writer.println(g.sample());
                }

                writer.flush();
                writer.close();
            }
            catch (Exception ex) {}
        }
        else if (Main.REZIM == 'T')
        {
            TestSpojityEmpirickyGenerator test = new TestSpojityEmpirickyGenerator();
            test.test(0, false);
        }
    }
}