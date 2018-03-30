package com.aprivate.bane.yamb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int[] diceIds = new int[]{R.id.dice1, R.id.dice2, R.id.dice3,
            R.id.dice4, R.id.dice5, R.id.dice6};

    ToggleButton[] diceButtons = new ToggleButton[6];
    Hand hand;

    Button roolButton;
    boolean daLiMoguDaBacam;

    //dole kolona
    int enablovanUDoleKoloni;
    int sumaNaDoleBrojevi;
    int sumaNaDoleMinMax;
    int sumaNaDolePoker;
    int kolikoImaKecevaDole;

    //slobodna kolona
    int enablovanUSlobodnojKoloni;
    int sumaBrojeviSlobodnaKolona;
    int sumaMinMaxSlobodnaKolona;
    int sumaPokerSlobodnaKolona;
    int kolikoImaKecevaSlobodnaKolona;

    //gore kolona
    int enablovanUGoreKoloni;
    int sumaNaGoreBrojevi;
    int sumaNaGoreMinMax;
    int sumaNaGorePoker;

    //poslednjaKolona
    int enablovanUPoslednjojKoloni;
    int sumaPoslednjaKolonaBrojevi;
    int sumaPoslednjaKolonaMinMax;
    int sumaPoslednjaKolonaPoker;
    int kolikoImaKecevaPoslednjaKolona;

    int sumaDrugeKolone;
    int sumaTreceKolone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < diceIds.length; i++) {
            ToggleButton button = findViewById(diceIds[i]);
            diceButtons[i] = button;
            diceButtons[i].setEnabled(false);
        }

        enablovanUDoleKoloni = R.id.a1;
        postaviPoljeZaKlikcUDoleKoloni();
        enablovanUGoreKoloni = R.id.c13;
        postaviPoljeZaKlikcUGoreKoloni();


        hand = new Hand();
        daLiMoguDaBacam = true;
        roolButton = findViewById(R.id.nextThrow);
        roolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rool();
            }
        });
        setRoolButtonText();
    }

    private void rool() {
        if (hand.bacanje != Hand.Bacanje.KRAJ) {
            List<Integer> niz = new ArrayList<>();
            switch (hand.bacanje) {
                case POCETAK:
                    niz.addAll(Arrays.asList(0, 1, 2, 3, 4, 5));
                    hand.nextThrow(niz);
                    hand.bacanje = Hand.Bacanje.PRVO_BACANJE;
                    prikaziBacanje();
                    setRoolButtonText();
                    break;
                case PRVO_BACANJE:
                    for (int i = 0; i < diceButtons.length; i++) {
                        if (!diceButtons[i].isChecked()) {
                            niz.add(i);
                        }
                    }
                    hand.nextThrow(niz);
                    prikaziBacanje();
                    setRoolButtonText();
                    hand.bacanje = Hand.Bacanje.DRUGO_BACANJE;
                case DRUGO_BACANJE:
                    for (int i = 0; i < diceButtons.length; i++) {
                        if (!diceButtons[i].isChecked()) {
                            niz.add(i);
                        }
                    }
                    hand.nextThrow(niz);
                    prikaziBacanje();
                    setRoolButtonText();
                    hand.bacanje = Hand.Bacanje.TRECE_BACANJE;
                    break;
                case TRECE_BACANJE:
                    for (int i = 0; i < diceButtons.length; i++) {
                        if (!diceButtons[i].isChecked()) {
                            niz.add(i);
                        }
                    }
                    hand.nextThrow(niz);
                    prikaziBacanje();
                    setRoolButtonText();
                    hand.bacanje = Hand.Bacanje.KRAJ;
                    break;
            }
        } else {
            Toast.makeText(this, "Molim Vas upisite rezultat", Toast.LENGTH_SHORT).show();
        }
    }

    private void prikaziBacanje() {
        switch (hand.bacanje) {
            case POCETAK:
                for (int i = 0; i < diceButtons.length; i++) {
                    diceButtons[i].setEnabled(false);
                    diceButtons[i].setChecked(false);
                }
                break;
            case PRVO_BACANJE:
                for (int i = 0; i < diceButtons.length; i++) {
                    diceButtons[i].setEnabled(true);
                }
                break;
            case DRUGO_BACANJE:
                for (int i = 0; i < diceButtons.length; i++) {
                    diceButtons[i].setEnabled(true);
                }
                break;
            case TRECE_BACANJE:
                for (int i = 0; i < diceButtons.length; i++) {
                    diceButtons[i].setEnabled(false);
                    diceButtons[i].setChecked(false);
                }
                break;

        }
        for (int i = 0; i < diceButtons.length; i++) {
            if (hand.bacanje != Hand.Bacanje.POCETAK) {
                int value = hand.getKockica(i);
                diceButtons[i].setText(value + "");
                diceButtons[i].setTextOn(value + "");
                diceButtons[i].setTextOff(value + "");
            } else {
                diceButtons[i].setText("");
                diceButtons[i].setTextOn("");
                diceButtons[i].setTextOff("");
            }
        }
    }

    public void setRoolButtonText() {
        String roolButtonText = "";
        switch (hand.bacanje) {
            case POCETAK:
                roolButtonText = "Start";
                break;
            case PRVO_BACANJE:
            case DRUGO_BACANJE:
                roolButtonText = "Rool again";
                break;
            case TRECE_BACANJE:
                roolButtonText = "Start";
                break;
        }

        roolButton.setText(roolButtonText);
    }


    private void updateSumaNaDoleBrojevi() {
        TextView textView = findViewById(R.id.sumaA);
        textView.setText(sumaNaDoleBrojevi + "");
        updateSumePrveKolone();
    }

    private void updateSumaNaGoreBrojevi() {
        TextView textView = findViewById(R.id.sumaC);
        textView.setText(sumaNaGoreBrojevi + "");
        updateSumePrveKolone();
    }

    private int klikNaDoleKolonu(View view, int kockica, int sledecePolje) {
        int ukupno = 0;
        if (hand.bacanje != Hand.Bacanje.POCETAK) {
            ukupno = hand.kolikoImaKockica(kockica) * kockica;
            ((TextView) view).setText("" + ukupno);
            hand.bacanje = Hand.Bacanje.POCETAK;
            prikaziBacanje();
            setRoolButtonText();
            view.setEnabled(false);
            enablovanUDoleKoloni = sledecePolje;
            postaviPoljeZaKlikcUDoleKoloni();
            updateSumaNaDoleBrojevi();
        } else {
            Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
        }
        return ukupno;
    }

    public void postaviPoljeZaKlikcUDoleKoloni() {
        TextView textView = findViewById(enablovanUDoleKoloni);
        textView.setEnabled(true);
    }

    public void doleColonaClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.a1: {
                int ukupno = klikNaDoleKolonu(view, 1, R.id.a2);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
                kolikoImaKecevaDole = ukupno;
            }
            break;
            case R.id.a2: {
                int ukupno = klikNaDoleKolonu(view, 2, R.id.a3);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
            }
            break;
            case R.id.a3: {
                int ukupno = klikNaDoleKolonu(view, 3, R.id.a4);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
            }
            break;
            case R.id.a4: {
                int ukupno = klikNaDoleKolonu(view, 4, R.id.a5);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
            }
            break;
            case R.id.a5: {
                int ukupno = klikNaDoleKolonu(view, 5, R.id.a6);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
            }
            break;
            case R.id.a6: {
                int ukupno = klikNaDoleKolonu(view, 6, R.id.a7);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
            }
            break;

            case R.id.a7: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.max();
                    sumaNaDoleMinMax += ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a8;
                    postaviPoljeZaKlikcUDoleKoloni();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.a8: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.min();
                    sumaNaDoleMinMax -= ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a9;
                    postaviPoljeZaKlikcUDoleKoloni();
                    sumaNaDoleMinMax *= kolikoImaKecevaDole;
                    TextView suma = findViewById(R.id.sumaAminmax);
                    suma.setText(sumaNaDoleMinMax + "");
                    updateSumaDrugeKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.a9: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.triling();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaDolePoker += ukupno;
                    TextView suma = findViewById(R.id.sumaAveliki);
                    suma.setText(sumaNaDolePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a10;
                    postaviPoljeZaKlikcUDoleKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;


            case R.id.a10: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ((TextView) view).setText("" + ukupno);
                    ukupno = hand.kentaPoena();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    sumaNaDolePoker += ukupno;
                    TextView suma = findViewById(R.id.sumaAveliki);
                    suma.setText(sumaNaDolePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a11;
                    postaviPoljeZaKlikcUDoleKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.a11: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.full();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaDolePoker += ukupno;
                    TextView suma = findViewById(R.id.sumaAveliki);
                    suma.setText(sumaNaDolePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a12;
                    postaviPoljeZaKlikcUDoleKoloni();
                    updateSumaTreceKolone();

                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.a12: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.poker();

                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaDolePoker += ukupno;
                    TextView suma = findViewById(R.id.sumaAveliki);
                    suma.setText(sumaNaDolePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a13;
                    postaviPoljeZaKlikcUDoleKoloni();

                    updateSumaTreceKolone();

                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.a13: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.yumb();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaDolePoker += ukupno;
                    TextView suma = findViewById(R.id.sumaAveliki);
                    suma.setText(sumaNaDolePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    doWeNeedToEnableLastRow();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

        }
    }

    private void doWeNeedToEnableLastRow() {
        TextView a13 = findViewById(R.id.a13);
        if (a13.isEnabled()) return;
        TextView b1 = findViewById(R.id.b1);
        TextView b2 = findViewById(R.id.b2);
        TextView b3 = findViewById(R.id.b3);
        TextView b4 = findViewById(R.id.b4);
        TextView b5 = findViewById(R.id.b5);
        TextView b6 = findViewById(R.id.b6);
        TextView b7 = findViewById(R.id.b7);
        TextView b8 = findViewById(R.id.b8);
        TextView b9 = findViewById(R.id.b9);
        TextView b10 = findViewById(R.id.b10);
        TextView b11 = findViewById(R.id.b11);
        TextView b12 = findViewById(R.id.b12);
        TextView b13 = findViewById(R.id.b13);
        if (b1.isEnabled()) return;
        if (b2.isEnabled()) return;
        if (b3.isEnabled()) return;
        if (b4.isEnabled()) return;
        if (b5.isEnabled()) return;
        if (b6.isEnabled()) return;
        if (b7.isEnabled()) return;
        if (b8.isEnabled()) return;
        if (b9.isEnabled()) return;
        if (b10.isEnabled()) return;
        if (b11.isEnabled()) return;
        if (b12.isEnabled()) return;
        if (b13.isEnabled()) return;
        TextView c1 = findViewById(R.id.c1);
        if (c1.isEnabled()) return;
        enablovanUPoslednjojKoloni = R.id.d1;
        postaviPoljeZaKlikcUPoslednjojKoloni();
    }

    private int klikNaGoreKolonu(View view, int kockica, int sledecePolje) {
        int ukupno = 0;
        if (hand.bacanje != Hand.Bacanje.POCETAK) {
            ukupno = hand.kolikoImaKockica(kockica) * kockica;
            ((TextView) view).setText("" + ukupno);
            hand.bacanje = Hand.Bacanje.POCETAK;
            prikaziBacanje();
            setRoolButtonText();
            view.setEnabled(false);
            enablovanUGoreKoloni = sledecePolje;
            if (sledecePolje != 0) {
                postaviPoljeZaKlikcUGoreKoloni();
            }
        } else {
            Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
        }
        return ukupno;
    }

    public void postaviPoljeZaKlikcUGoreKoloni() {
        TextView textView = findViewById(enablovanUGoreKoloni);
        textView.setEnabled(true);
    }

    public void goreColonaClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.c13: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.yumb();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGorePoker += ukupno;
                    TextView textView = findViewById(R.id.sumaCveliki);
                    textView.setText(sumaNaGorePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c12;
                    postaviPoljeZaKlikcUGoreKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c12: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.poker();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGorePoker += ukupno;
                    TextView textView = findViewById(R.id.sumaCveliki);
                    textView.setText(sumaNaGorePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c11;
                    postaviPoljeZaKlikcUGoreKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c11: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.full();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGorePoker += ukupno;
                    TextView textView = findViewById(R.id.sumaCveliki);
                    textView.setText(sumaNaGorePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c10;
                    postaviPoljeZaKlikcUGoreKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c10: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.kentaPoena();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGorePoker += ukupno;
                    TextView textView = findViewById(R.id.sumaCveliki);
                    textView.setText(sumaNaGorePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c9;
                    postaviPoljeZaKlikcUGoreKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c9: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.triling();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGorePoker += ukupno;
                    TextView textView = findViewById(R.id.sumaCveliki);
                    textView.setText(sumaNaGorePoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c8;
                    postaviPoljeZaKlikcUGoreKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c8: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.min();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaNaGoreMinMax -= ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c7;
                    postaviPoljeZaKlikcUGoreKoloni();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.c7: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.max();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    sumaNaGoreMinMax += ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUGoreKoloni = R.id.c6;
                    postaviPoljeZaKlikcUGoreKoloni();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;


            case R.id.c6: {
                int ukupno = klikNaGoreKolonu(view, 6, R.id.c5);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
            }
            break;
            case R.id.c5: {
                int ukupno = klikNaGoreKolonu(view, 5, R.id.c4);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
            }
            break;
            case R.id.c4: {
                int ukupno = klikNaGoreKolonu(view, 4, R.id.c3);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
            }
            break;
            case R.id.c3: {
                int ukupno = klikNaGoreKolonu(view, 3, R.id.c2);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
            }
            break;
            case R.id.c2: {
                int ukupno = klikNaGoreKolonu(view, 2, R.id.c1);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
            }
            break;
            case R.id.c1: {
                int ukupno = klikNaGoreKolonu(view, 1, 0);
                sumaNaGoreBrojevi += ukupno;
                updateSumaNaGoreBrojevi();
                sumaNaGoreMinMax *= ukupno;
                TextView suma = findViewById(R.id.sumaCminmax);
                suma.setText(sumaNaGoreMinMax + "");
                updateSumaDrugeKolone();
                doWeNeedToEnableLastRow();
            }
            break;
        }
    }


    public void SlobodnaColonaClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.b1: {
                int ukupno = klikNaSlobodnuKolonu(view, 1);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
                kolikoImaKecevaSlobodnaKolona = ukupno;
                izracunajSlobodnaMinMax();
            }
            break;
            case R.id.b2: {
                int ukupno = klikNaSlobodnuKolonu(view, 2);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
            }
            break;
            case R.id.b3: {
                int ukupno = klikNaSlobodnuKolonu(view, 3);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
            }
            break;
            case R.id.b4: {
                int ukupno = klikNaSlobodnuKolonu(view, 4);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
            }
            break;
            case R.id.b5: {
                int ukupno = klikNaSlobodnuKolonu(view, 5);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
            }
            break;
            case R.id.b6: {
                int ukupno = klikNaSlobodnuKolonu(view, 6);
                sumaBrojeviSlobodnaKolona += ukupno;
                updateSumaSlobodnaKolonaBrojevi();
            }
            break;

            case R.id.b7: {
                int ukupno;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.max();
                    sumaBrojeviSlobodnaKolona += ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    izracunajSlobodnaMinMax();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.b8: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.min();
                    sumaMinMaxSlobodnaKolona -= ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    izracunajSlobodnaMinMax();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.b9: {
                int ukupno;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.triling();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPokerSlobodnaKolona += ukupno;
                    TextView suma = findViewById(R.id.sumaBveliki);
                    suma.setText(sumaPokerSlobodnaKolona + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;


            case R.id.b10: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.kentaPoena();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPokerSlobodnaKolona += ukupno;
                    TextView suma = findViewById(R.id.sumaBveliki);
                    suma.setText(sumaPokerSlobodnaKolona + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a11;
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.b11: {
                int ukupno;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.full();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPokerSlobodnaKolona += ukupno;
                    TextView suma = findViewById(R.id.sumaBveliki);
                    suma.setText(sumaPokerSlobodnaKolona + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a12;
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.b12: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.poker();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPokerSlobodnaKolona += ukupno;
                    TextView suma = findViewById(R.id.sumaBveliki);
                    suma.setText(sumaPokerSlobodnaKolona + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUDoleKoloni = R.id.a13;
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.b13: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.yumb();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPokerSlobodnaKolona += ukupno;
                    TextView suma = findViewById(R.id.sumaBveliki);
                    suma.setText(sumaPokerSlobodnaKolona + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void izracunajSlobodnaMinMax() {
        TextView jedinice = findViewById(R.id.b1);
        TextView max = findViewById(R.id.b7);
        TextView min = findViewById(R.id.b8);
        if (max.isEnabled() || min.isEnabled() || jedinice.isEnabled()) {
            return;
        }
        int j = Integer.parseInt(jedinice.getText().toString());
        int mx = Integer.parseInt(max.getText().toString());
        int mn = Integer.parseInt(min.getText().toString());
        int suma = (mx-mn) * j;
        TextView textView = findViewById(R.id.sumaBminmax);
        textView.setText(suma + "");
        updateSumaDrugeKolone();
    }

    private int klikNaSlobodnuKolonu(View view, int kockica) {
        int ukupno = 0;
        if (hand.bacanje != Hand.Bacanje.POCETAK) {
            ukupno = hand.kolikoImaKockica(kockica) * kockica;
            ((TextView) view).setText("" + ukupno);
            hand.bacanje = Hand.Bacanje.POCETAK;
            prikaziBacanje();
            setRoolButtonText();
            view.setEnabled(false);
            updateSumaSlobodnaKolonaBrojevi();
        } else {
            Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
        }
        return ukupno;
    }

    private void updateSumaSlobodnaKolonaBrojevi() {
        TextView textView = findViewById(R.id.sumaB);
        textView.setText(sumaBrojeviSlobodnaKolona + "");
        updateSumePrveKolone();
    }

    private void updateSumePrveKolone() {
        TextView textView = findViewById(R.id.sumaPrvihKolona);
        int ukupno = sumaBrojeviSlobodnaKolona + sumaNaDoleBrojevi + sumaNaGoreBrojevi + sumaPoslednjaKolonaBrojevi;
        textView.setText(ukupno + "");
        setUkupnaSuma();
    }

    private void updateSumaDrugeKolone() {
        TextView textView = findViewById(R.id.sumaDrugihKolona);
        int ukupno = sumaNaDoleMinMax + sumaNaGoreMinMax + sumaMinMaxSlobodnaKolona + sumaPoslednjaKolonaMinMax;
        textView.setText(ukupno + "");
        setUkupnaSuma();
    }

    private void updateSumaTreceKolone() {
        TextView textView = findViewById(R.id.sumaTrecihKolona);
        int ukupno = sumaNaDolePoker + sumaNaDolePoker + sumaPokerSlobodnaKolona + sumaPoslednjaKolonaPoker;
        textView.setText(ukupno + "");
        setUkupnaSuma();
    }

    private void setUkupnaSuma() {
        TextView textView =  findViewById(R.id.ukupnaSuma);
        int up = sumaBrojeviSlobodnaKolona + sumaNaDoleBrojevi + sumaNaGoreBrojevi + sumaPoslednjaKolonaBrojevi;
        int ud = sumaNaDoleMinMax + sumaNaGoreMinMax + sumaMinMaxSlobodnaKolona + sumaPoslednjaKolonaMinMax;
        int ut = sumaNaDolePoker + sumaNaDolePoker + sumaPokerSlobodnaKolona + sumaPoslednjaKolonaPoker;
        int ukupno = up + ud + ut;
        textView.setText(ukupno + "");
    }

    public void poslednjaColonaClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.d1: {
                int ukupno = klikNaPoslednjuKolonu(view, 1, R.id.d2);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
                kolikoImaKecevaPoslednjaKolona = ukupno;
            }
            break;
            case R.id.d2: {
                int ukupno = klikNaPoslednjuKolonu(view, 2, R.id.d3);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
            }
            break;
            case R.id.d3: {
                int ukupno = klikNaPoslednjuKolonu(view, 3, R.id.d4);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
            }
            break;
            case R.id.d4: {
                int ukupno = klikNaPoslednjuKolonu(view, 4, R.id.d5);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
            }
            break;
            case R.id.d5: {
                int ukupno = klikNaPoslednjuKolonu(view, 5, R.id.d6);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
            }
            break;
            case R.id.d6: {
                int ukupno = klikNaPoslednjuKolonu(view, 6, R.id.d7);
                sumaPoslednjaKolonaBrojevi += ukupno;
                updateSumaPoslednjaKolonaBrojevi();
            }
            break;

            case R.id.d7: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.max();
                    sumaPoslednjaKolonaMinMax += ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d8;
                    postaviPoljeZaKlikcUPoslednjojKoloni();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.d8: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.min();
                    sumaPoslednjaKolonaMinMax -= ukupno;
                    ((TextView) view).setText("" + ukupno);
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d9;
                    postaviPoljeZaKlikcUPoslednjojKoloni();
                    sumaPoslednjaKolonaMinMax *= kolikoImaKecevaPoslednjaKolona;
                    TextView suma = findViewById(R.id.sumaDminmax);
                    suma.setText(sumaPoslednjaKolonaMinMax + "");
                    updateSumaDrugeKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.d9: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.triling();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPoslednjaKolonaPoker += ukupno;
                    TextView suma = findViewById(R.id.sumaDveliki);
                    suma.setText(sumaPoslednjaKolonaPoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d10;
                    postaviPoljeZaKlikcUPoslednjojKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;


            case R.id.d10: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ((TextView) view).setText("" + ukupno);
                    ukupno = hand.kentaPoena();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    sumaPoslednjaKolonaPoker += ukupno;
                    TextView suma = findViewById(R.id.sumaDveliki);
                    suma.setText(sumaPoslednjaKolonaPoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d11;
                    postaviPoljeZaKlikcUPoslednjojKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.d11: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.full();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPoslednjaKolonaPoker += ukupno;
                    TextView suma = findViewById(R.id.sumaDveliki);
                    suma.setText(sumaPoslednjaKolonaPoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d12;
                    postaviPoljeZaKlikcUPoslednjojKoloni();
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.d12: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.poker();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPoslednjaKolonaPoker += ukupno;
                    TextView suma = findViewById(R.id.sumaDveliki);
                    suma.setText(sumaPoslednjaKolonaPoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    enablovanUPoslednjojKoloni = R.id.d13;
                    postaviPoljeZaKlikcUPoslednjojKoloni();

                    updateSumaTreceKolone();

                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.d13: {
                int ukupno = 0;
                if (hand.bacanje != Hand.Bacanje.POCETAK) {
                    ukupno = hand.yumb();
                    if (ukupno == -1) {
                        ukupno = 0;
                    }
                    ((TextView) view).setText("" + ukupno);
                    sumaPoslednjaKolonaPoker += ukupno;
                    TextView suma = findViewById(R.id.sumaDveliki);
                    suma.setText(sumaPoslednjaKolonaPoker + "");
                    hand.bacanje = Hand.Bacanje.POCETAK;
                    prikaziBacanje();
                    setRoolButtonText();
                    view.setEnabled(false);
                    updateSumaTreceKolone();
                } else {
                    Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void updateSumaPoslednjaKolonaBrojevi() {
        TextView textView = findViewById(R.id.sumaD);
        textView.setText(sumaPoslednjaKolonaBrojevi + "");
        updateSumePrveKolone();
    }

    private int klikNaPoslednjuKolonu(View view, int kockica, int sledecePolje) {
        int ukupno = 0;
        if (hand.bacanje != Hand.Bacanje.POCETAK) {
            ukupno = hand.kolikoImaKockica(kockica) * kockica;
            ((TextView) view).setText("" + ukupno);
            hand.bacanje = Hand.Bacanje.POCETAK;
            prikaziBacanje();
            setRoolButtonText();
            view.setEnabled(false);
            enablovanUPoslednjojKoloni = sledecePolje;
            postaviPoljeZaKlikcUPoslednjojKoloni();
        } else {
            Toast.makeText(this, "Molim Vas bacite kockicu barem jednom", Toast.LENGTH_SHORT).show();
        }
        return ukupno;
    }

    private void postaviPoljeZaKlikcUPoslednjojKoloni() {
        TextView textView = findViewById(enablovanUPoslednjojKoloni);
        textView.setEnabled(true);
    }
}