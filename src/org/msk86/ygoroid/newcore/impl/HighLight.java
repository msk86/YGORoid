package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.HighLightRenderer;
import org.msk86.ygoroid.size.Size;

public class HighLight implements Item {
    private Item target;
    private Size size;

    public HighLight(Item target) {
        this.target = target;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
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
