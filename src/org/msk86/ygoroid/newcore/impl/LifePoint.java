package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.LifePointRenderer;

public class LifePoint implements Item {
    int[] lps = new int[2];

    public LifePoint() {
        lps[0] = 8000;
        lps[1] = 8000;
    }

    public int[] getLps() {
        return lps;
    }

    public int getLp(int idx) {
        return lps[idx];
    }

    public void setLp(int lp, int idx) {
        lps[idx] = lp;
    }

    public void reset() {
        lps[0] = 8000;
        lps[1] = 8000;
    }

    @Override
    public String toString() {
        String str = "LP: " + lps[0];
        if(lps[1] != 8000) {
            str = "LP1:" + lps[0] + "\nLP2:" + lps[1];
        }
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
