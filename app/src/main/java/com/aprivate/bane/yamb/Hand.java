package com.aprivate.bane.yamb;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Hand {


    public static int[] hand;
    int rez[];
    private int numberOfDiceces;
    private int numberOfSides;
    Random random;
    Bacanje bacanje;


    public Hand() {
        this.numberOfDiceces = 6;
        this.numberOfSides = 6;
        hand = new int[numberOfDiceces];
        rez = new int[numberOfSides];
        random = new Random();
        bacanje = Bacanje.POCETAK;
    }

    public Hand(int numberOfDiceces, int numberOfSides) {
        this.numberOfDiceces = numberOfDiceces;
        this.numberOfSides = numberOfSides;
        hand = new int[numberOfDiceces];
        rez = new int[numberOfSides];
        random = new Random();
        bacanje = Bacanje.POCETAK;
    }

    public void nextThrow(List<Integer> izborKockica) {
        switch (bacanje) {
            case POCETAK:
                generisiNoveKockice(izborKockica);
                izbrojKockice();
                break;
            case PRVO_BACANJE:
                generisiNoveKockice(izborKockica);
                izbrojKockice();
                break;
            case DRUGO_BACANJE:
                generisiNoveKockice(izborKockica);
                izbrojKockice();
                break;
            case TRECE_BACANJE:
                System.out.println("Ne moze, ruka je gotova, mora reset");
                break;
        }
    }

    private void generisiNoveKockice(List<Integer> izborKockica) {
        for (int i = 0; i < izborKockica.size(); i++) {
            int kockicaKojuMenjamo = izborKockica.get(i);
            hand[kockicaKojuMenjamo] = 1 + random.nextInt(numberOfSides);
        }
    }

    public int yumb() {
        return izracunajSkor(daLiImaBarem(5), 5);
    }

    public int poker() {
        return izracunajSkor(daLiImaBarem(4), 4);
    }

    public int triling() {
        return izracunajSkor(daLiImaBarem(3), 3);
    }

    public boolean kenta() {
        return (rez[1] >= 1 && rez[2] >= 1 && rez[3] >= 1 && rez[4] >= 1) && (rez[0] >= 1 || rez[5] >= 1);
    }

    public int full() {
        int maxTrojke = daLiImaBarem(3);
        if (maxTrojke == -1) {
            return -1;
        }
        int maxDvojke = -1;
        for (int i = numberOfSides - 1; i >= 0; i--) {
            if (i != (maxTrojke - 1)) {
                if (rez[i] >= 2) {
                    maxDvojke = (i + 1);
                    break;
                }
            }
        }
        if (maxDvojke == -1) {
            return -1;
        }
        return maxTrojke * 3 + maxDvojke * 2;
    }

    public int max() {
        int sum = 0;
        int min = numberOfSides + 1;
        for (int i = 0; i < numberOfDiceces; i++) {
            sum += hand[i];
            if (hand[i] < min) {
                min = hand[i];
            }
        }
        return sum - min;
    }

    public int min() {
        int copy[] = Arrays.copyOf(hand, hand.length);
        Arrays.sort(copy);
        int sum = 0;
        for (int i = 0; i < numberOfDiceces - 1; i++) {
            sum += copy[i];
        }
        return sum;
    }

    private int daLiImaBarem(int x) {
        for (int i = numberOfSides - 1; i >= 0; i--) {
            if (rez[i] >= x) {
                return (i + 1);
            }
        }
        return -1;
    }

    private int izracunajSkor(int kockica, int kolikoIhIma) {
        if (kockica == -1) {
            return -1;
        }
        return kockica * kolikoIhIma;
    }

    private void izbrojKockice() {
        rez = new int[numberOfSides];
        for (int i = 0; i < numberOfDiceces; i++) {
            rez[hand[i]-1]++;
        }
    }

    public int kolikoImaKockica(int n) {
        if (n >=1 && n <= numberOfDiceces) {
            return rez[n-1];
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < numberOfDiceces - 1; i++) {
            stringBuilder.append(hand[i])
                    .append(" ");
        }
        stringBuilder.append(hand[numberOfDiceces - 1]).append("]");

        stringBuilder.append(" stats: [");
        for (int i = 0; i < numberOfSides-1; i++) {
            stringBuilder.append(i+1).append(":").append(rez[i]).append(", ");
        }
        stringBuilder.append(numberOfSides).append(":").append(rez[numberOfSides-1]).append("]");
        stringBuilder.append("{");
        int yumb = yumb();
        if (yumb != -1) {
            stringBuilder.append("yumb:").append(yumb).append(", ");
        }
        int poker = poker();
        if (poker != -1) {
            stringBuilder.append("poker:").append(poker).append(", ");
        }

        int full = full();
        if (full != -1) {
            stringBuilder.append("full:").append(full).append(", ");
        }

        if (kenta()) {
            stringBuilder.append("kenta, ");
        }
        int triling = triling();
        if (triling != -1) {
            stringBuilder.append("triling:").append(triling).append(", ");
        }
        stringBuilder.append("max:").append(max()).append(", ");
        stringBuilder.append("min:").append(min()).append(", ");
        stringBuilder.append("Stanje: ").append(bacanje.toString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void zadrziKockice() {

    }

    public int getKockica(int i) {
        return hand[i];
    }

    enum Bacanje {
        POCETAK,
        PRVO_BACANJE,
        DRUGO_BACANJE,
        TRECE_BACANJE,
        KRAJ
    }
}



