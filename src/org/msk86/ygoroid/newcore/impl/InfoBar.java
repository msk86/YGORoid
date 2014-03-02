package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Infoable;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.renderer.InfoBarRenderer;

public class InfoBar implements Item {
    private Infoable infoItem;
    private String info;

    private Item barHolder;

    public InfoBar(Item barHolder) {
        this.barHolder = barHolder;
    }

    public void setInfo(Infoable item) {
        infoItem = item;
        info = null;
    }

    public void setInfo(String info) {
        infoItem = null;
        this.info = info;
    }

    public void clearInfo() {
        this.infoItem = null;
        this.info = null;
    }

    public String info() {
        if(infoItem != null) {
            return this.infoItem.getInfo();
        } else if(this.info != null) {
            return this.info;
        }
        return "";
    }

    public Infoable getInfoItem() {
        return infoItem;
    }

    public Item getBarHolder() {
        return barHolder;
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new InfoBarRenderer(this);
        }
        return renderer;
    }
}
