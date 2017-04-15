package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.constant.bmp.FieldBackgroundGenerator;
import org.msk86.ygoroid.newcore.impl.layout.CenterLayout;
import org.msk86.ygoroid.newcore.impl.renderer.FieldRenderer;

public class Field implements Item, Container, Bmpable {
    private DuelFields duelFields;
    private FieldType type;
    private Item item;
    private int backgroundResId;
    private BmpGenerator generator = new FieldBackgroundGenerator(this);

    public Field(FieldType type, DuelFields duelFields) {
        this(type, -1, duelFields);
    }

    public Field(FieldType type, int backgroundResId, DuelFields duelFields) {
        this.type = type;
        this.duelFields = duelFields;
        this.backgroundResId = backgroundResId;
    }

    public DuelFields getDuelFields() {
        return duelFields;
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

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public Item removeItem() {
        Item item = this.item;
        this.item = null;
        return item;
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



    @Override
    public BmpGenerator getBmpGenerator() {
        return generator;
    }
}
