package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.CoinRenderer;

import java.util.Random;

public class Coin implements Item {
    private int coinNumber;
    private Random random;

    public Coin() {
        coinNumber = 1;
        random = new Random();
    }

    public void throwCoin() {
        coinNumber = random.nextInt(2);
    }

    public int getCoinNumber() {
        return coinNumber;
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
