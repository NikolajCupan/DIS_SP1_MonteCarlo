package org.example;

import org.example.GUI.HlavneOkno;
import org.example.Generatory.Ostatne.GeneratorNasad;
import org.example.Generatory.Ostatne.SkupinaEmpirickyGenerator;
import org.example.Generatory.SpojityEmpirickyGenerator;
import org.example.Generatory.SpojityRovnomernyGenerator;

import java.io.PrintWriter;

public class Main
{
    public static void main(String[] args)
    {
        //HlavneOkno hl = new HlavneOkno();

        SkupinaEmpirickyGenerator[] skupiny = new SkupinaEmpirickyGenerator[] {
                new SkupinaEmpirickyGenerator(0.10, 0.30, 0.10),
                new SkupinaEmpirickyGenerator(0.30, 0.80, 0.35),
                new SkupinaEmpirickyGenerator(0.80, 1.20, 0.20),
                new SkupinaEmpirickyGenerator(1.20, 2.50, 0.15),
                new SkupinaEmpirickyGenerator(2.50, 3.80, 0.15),
                new SkupinaEmpirickyGenerator(3.80, 4.80, 0.05)
        };
        SpojityEmpirickyGenerator spojityEmpirickyGenerator = new SpojityEmpirickyGenerator(skupiny, new GeneratorNasad());

        try
        {
            PrintWriter writer = new PrintWriter("test.txt");

            for (int i = 0; i < 1000000; i++)
            {
                writer.println(spojityEmpirickyGenerator.sample());
            }
        }
        catch (Exception e)
        {

        }
    }
}