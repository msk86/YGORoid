package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.anime.FlashMask;
import org.msk86.ygoroid.newcore.impl.renderer.DiceRenderer;

import java.util.Random;

public class Dice implements Item {
    private int diceNumber;
    private Random random;
    private FlashMask mask;

    public Dice() {
        diceNumber = 6;
        random = new Random();
        mask = new FlashMask(this);
    }

    public void reset() {
        diceNumber = 6;
    }

    public void throwDice() {
        diceNumber = random.nextInt(6) + 1;
        mask.refresh();
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

    public FlashMask getMask() {
        return mask;
    }
}
