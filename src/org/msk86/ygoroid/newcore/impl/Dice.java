package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.DiceRenderer;

import java.util.Random;

public class Dice implements Item {
    private int diceNumber;
    private Random random;

    public Dice() {
        diceNumber = 6;
        random = new Random();
    }

    public void throwDice() {
        diceNumber = random.nextInt(6) + 1;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new DiceRenderer(this);
        }
        return renderer;
    }
}
