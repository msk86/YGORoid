package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.IndicatorRenderer;

public class Indicator implements Item {

    private int count = 0;

    public void increase() {
        count++;
    }

    public void decrease() {
        if (count == 0) {
            return;
        }
        count--;
    }

    public void clear() {
        count = 0;
    }

    public int getCount() {
        return count;
    }


    private IndicatorRenderer renderer;

    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new IndicatorRenderer(this);
        }
        return renderer;
    }
}
