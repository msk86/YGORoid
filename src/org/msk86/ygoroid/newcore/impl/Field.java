package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.layout.CenterLayout;
import org.msk86.ygoroid.newcore.impl.renderer.FieldRenderer;

public class Field implements Item, Container {
    private FieldType type;
    private Item item;

    public Field(FieldType type) {
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item removeItem() {
        Item item = this.item;
        this.item = null;
        return item;
    }

    public Card getTopCard() {
        return (Card) item;
    }

    private Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new FieldRenderer(this);
        }
        return renderer;
    }

    @Override
    public Layout getLayout() {
        return new CenterLayout(this, item);
    }
}
