package com.aprivate.bane.yamb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < diceIds.length; i++) {
            ToggleButton button = findViewById(diceIds[i]);
            diceButtons[i] = button;
            diceButtons[i].setEnabled(false);
        }

        hand = new Hand();


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
        List<Integer> niz = new ArrayList<>();
        if (hand.bacanje == Hand.Bacanje.TRECE_BACANJE) {
            for (int i = 0; i < diceButtons.length; i++) {
                diceButtons[i].setChecked(false);
            }
            hand.bacanje = Hand.Bacanje.POCETAK;
            niz.addAll(Arrays.asList(0, 1, 2, 3, 4, 5));
        } else {
            for (int i = 0; i < diceButtons.length; i++) {
                if (!diceButtons[i].isChecked()) {
                    niz.add(i);
                }
            }
        }
        hand.nextThrow(niz);
        prikaziBacanje();
        setRoolButtonText();
    }

    private void prikaziBacanje() {
        if (hand.bacanje == Hand.Bacanje.TRECE_BACANJE) {
            for (int i = 0; i < diceButtons.length; i++) {
                diceButtons[i].setEnabled(false);
            }
        } else if (hand.bacanje != Hand.Bacanje.POCETAK) {
            for (int i = 0; i < diceButtons.length; i++) {
                diceButtons[i].setEnabled(true);
            }
        }
        for (int i = 0; i < diceButtons.length; i++) {
            int value = hand.getKockica(i);
            diceButtons[i].setText(value + "");
            diceButtons[i].setTextOn(value + "");
            diceButtons[i].setTextOff(value + "");
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

    public void doleColonaClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.a1:
                int ukupnoJedinica = hand.kolikoImaKockica(1);
                ((TextView) view).setText("" + ukupnoJedinica);
                break;
        }
    }
}
