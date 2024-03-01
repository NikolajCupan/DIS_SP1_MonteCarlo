package org.example.Generatory.Ostatne;

public class DeterministickyGenerator<T>
{
    private final T hodnota;

    public DeterministickyGenerator(T hodnota)
    {
        this.hodnota = hodnota;
    }

    public T sample()
    {
        return this.hodnota;
    }
}
