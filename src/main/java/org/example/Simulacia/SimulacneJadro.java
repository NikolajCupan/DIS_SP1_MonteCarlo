package org.example.Simulacia;

public abstract class SimulacneJadro
{
    public boolean ukonci = false;

    public void simuluj(int pocetReplikacii, int nasada, boolean pouziNasadu)
    {
        this.validujVstupy(pocetReplikacii);

        this.predReplikaciami(nasada, pouziNasadu);

        for (int i = 0; i < pocetReplikacii; i++)
        {
            predReplikaciou();
            replikacia();
            poReplikacii();

            if (this.ukonci)
            {
                break;
            }
        }

        this.poReplikaciach();
    }

    private void validujVstupy(int pocetReplikacii)
    {
        if (pocetReplikacii < 1)
        {
            throw new RuntimeException("Pocet replikacii nemoze byt mensi ako 1!");
        }
    }

    public void ukonci()
    {
        this.ukonci = true;
    }

    protected abstract void predReplikaciami(int nasada, boolean pouziNasadu);
    protected abstract void poReplikaciach();
    protected abstract void predReplikaciou();
    protected abstract void poReplikacii();
    protected abstract void replikacia();
}
