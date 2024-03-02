package org.example;

import org.example.GUI.HlavneOkno;

public class Main
{
    public static char REZIM = 'G';

    public static void main(String[] args)
    {
        if (Main.REZIM == 'G')
        {
            new HlavneOkno();
        }
        else if (Main.REZIM == 'T')
        {
            // TEST
        }
    }
}