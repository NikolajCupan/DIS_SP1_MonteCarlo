package org.example;

import org.example.Generatory.*;
import org.example.Generatory.Ostatne.GeneratorNasad;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            PrintWriter zapisovac = new PrintWriter("test.txt", StandardCharsets.UTF_8);
            GeneratorNasad generatorNasad = new GeneratorNasad();

            SpojityRovnomernyGenerator g = new SpojityRovnomernyGenerator(0.9, 2.2, generatorNasad);
            for (int i = 0; i < 1000000; i++)
            {
                double sample = g.sample();
                zapisovac.println(sample);
                //System.out.println(sample);
            }

            zapisovac.flush();
            zapisovac.close();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }

    }
}