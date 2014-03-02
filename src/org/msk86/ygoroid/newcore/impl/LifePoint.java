package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.LifePointRenderer;

public class LifePoint implements Item {
    int lp;

    public LifePoint() {
        lp = 8000;
    }

    public int getLp() {
        return lp;
    }

    @Override
    public String toString() {
        return "LP: " + lp;
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
