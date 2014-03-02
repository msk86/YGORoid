package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.LifePointRenderer;

public class LifePoint implements Item {
    int lp, opponentLp;

    public LifePoint() {
        lp = 8000;
        opponentLp = 8000;
    }

    public int getLp() {
        return lp;
    }

    public int getOpponentLp() {
        return opponentLp;
    }

    @Override
    public String toString() {
        String str = "LP: " + lp;
        str += (opponentLp == 8000 ? "" : " / " + opponentLp);
        return str;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new LifePointRenderer(this);
        }
        return renderer;
    }
}
