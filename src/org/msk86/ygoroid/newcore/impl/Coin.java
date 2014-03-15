package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.anime.FlashMask;
import org.msk86.ygoroid.newcore.impl.renderer.CoinRenderer;

import java.util.Random;

public class Coin implements Item {
    private int coinNumber;
    private Random random;
    private FlashMask mask;

    public Coin() {
        coinNumber = 1;
        random = new Random();
        mask = new FlashMask(this);
    }

    public void reset() {
        coinNumber = 1;
    }

    public void throwCoin() {
        coinNumber = random.nextInt(2);
        mask.refresh();
    }

    public int getCoinNumber() {
        return coinNumber;
    }

    public FlashMask getMask() {
        return mask;
    }

    public void setCoinNumber(int coinNumber) {
        this.coinNumber = coinNumber;
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new CoinRenderer(this);
        }
        return renderer;
    }
}
