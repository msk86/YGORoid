package org.msk86.ygoroid.newcore.anime;

import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.Bmpable;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.anime.bmp.FlashMaskGenerator;
import org.msk86.ygoroid.newcore.anime.renderer.FlashMaskRenderer;

import java.util.Date;

public class FlashMask implements Item, Bmpable {
    Item targetItem;
    boolean alive;
    int life;
    long refreshTime;

    public FlashMask(Item targetItem) {
        this.targetItem = targetItem;
        alive = false;
        life = 1000;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public void refresh() {
        refreshTime = new Date().getTime();
        alive = true;
    }

    public int getLife() {
        return life;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new FlashMaskRenderer(this);
        }
        return renderer;
    }


    BmpGenerator generator;
    @Override
    public BmpGenerator getBmpGenerator() {
        if(generator == null) {
            generator = new FlashMaskGenerator(this);
        }
        return generator;
    }
}
