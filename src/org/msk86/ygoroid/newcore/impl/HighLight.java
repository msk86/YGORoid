package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.HighLightRenderer;

public class HighLight implements Item {
    private Item target;

    public HighLight(Item target) {
        this.target = target;
    }

    public Item getTarget() {
        return target;
    }

    private Renderer renderer;

    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new HighLightRenderer(this);
        }
        return renderer;
    }
}
