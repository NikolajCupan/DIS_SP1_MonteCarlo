package org.example.Simulacia;

public abstract class SimulacneJadro
{
    private boolean pokracuj = true;
    protected int pocetReplikacii;

    public void simuluj(int pocetReplikacii, int nasada, boolean pouziNasadu)
    {
        this.validujVstupy(pocetReplikacii);
        this.pocetReplikacii = pocetReplikacii;

        // Samotne simulacne jadro
        this.predReplikaciami(nasada, pouziNasadu);

        for (int i = 0; i < this.pocetReplikacii; i++)
        {
            predReplikaciou();
            replikacia();
            poReplikacii();

            if (!this.pokracuj)
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

    public void ukonciSimulaciu()
    {
        this.pokracuj = false;
    }

    protected abstract void predReplikaciami(int nasada, boolean pouziNasadu);
    protected abstract void poReplikaciach();
    protected abstract void predReplikaciou();
    protected abstract void poReplikacii();
    protected abstract void replikacia();
}
