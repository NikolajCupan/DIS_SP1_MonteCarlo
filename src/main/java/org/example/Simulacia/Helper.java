package org.example.Simulacia;

public class Helper
{
    public static double mesacnaUrokovaSadzba(double rocnaUrokovaSadzba)
    {
        return (rocnaUrokovaSadzba / 100.0) / 12.0;
    }

    public static double mesacnaSplatka(double vyskaIstiny, double rocnaUrokovaSadzba, int zostavajuceRoky)
    {
        double mesacnaUrokovaSadzba = Helper.mesacnaUrokovaSadzba(rocnaUrokovaSadzba);

        double citatel = vyskaIstiny * mesacnaUrokovaSadzba * Math.pow(1 + mesacnaUrokovaSadzba, 12 * zostavajuceRoky);
        double menovatel = Math.pow(1 + mesacnaUrokovaSadzba, 12 * zostavajuceRoky) - 1;

        return citatel / menovatel;
    }

    public static double zostatokIstiny(double vyskaIstiny, double rocnaUrokovaSadzba, int zostavajuceRoky,
                                        int splateneRoky)
    {
        double mesacnaUrokovaSadzba = Helper.mesacnaUrokovaSadzba(rocnaUrokovaSadzba);

        double citatel = Math.pow(1 + mesacnaUrokovaSadzba, 12 * zostavajuceRoky)
            - Math.pow(1 + mesacnaUrokovaSadzba, 12 * splateneRoky);
        double menovatel = Math.pow(1 + mesacnaUrokovaSadzba, 12 * zostavajuceRoky) - 1;

        return vyskaIstiny * (citatel / menovatel);
    }
}
