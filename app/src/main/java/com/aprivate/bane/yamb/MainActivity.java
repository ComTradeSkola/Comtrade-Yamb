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
    int enablovanUDoleKoloni;
    int sumaNaDoleBrojevi;

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

    public void onClick(View view) {

    }



    private void updateSumaNaDoleBrojevi() {
        TextView textView = findViewById(R.id.sumaA);
        textView.setText(sumaNaDoleBrojevi + "");
    }

    private void izracunajSumu() {

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
            case R.id.a1:
                int ukupno = klikNaDoleKolonu(view, 1, R.id.a2);
                sumaNaDoleBrojevi += ukupno;
                updateSumaNaDoleBrojevi();
                break;
            case R.id.a2:
                klikNaDoleKolonu(view, 2, R.id.a3);
                break;
            case R.id.a3:
                klikNaDoleKolonu(view, 3, R.id.a4);
                break;
            case R.id.a4:
                klikNaDoleKolonu(view, 4, R.id.a5);
                break;
            case R.id.a5:
                klikNaDoleKolonu(view, 5, R.id.a6);
                break;
            case R.id.a6:
                klikNaDoleKolonu(view, 6, R.id.a7);
                izracunajSumu();
                break;
        }
    }
}
