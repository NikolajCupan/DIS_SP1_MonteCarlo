package org.example.Generatory.Ostatne;

import java.util.Random;

public class GeneratorNasad
{
    private final Random random;

    public GeneratorNasad()
    {
        this.random = new Random();
    }

    public GeneratorNasad(int nasada)
    {
        System.out.println("Bola pevne stanovena nasada generatora nasad: " + nasada + "!");
        this.random = new Random(nasada);
    }

    public int nasada()
    {
        return this.random.nextInt();
    }
}
