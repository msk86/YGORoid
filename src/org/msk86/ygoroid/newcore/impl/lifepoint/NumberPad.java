package org.msk86.ygoroid.newcore.impl.lifepoint;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.layout.GridLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.renderer.NumberPadRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberPad implements Item, Container {
    List<Item> items;

    public NumberPad() {
        items = new ArrayList<Item>();

        Collections.addAll(items, Numbers.values());

        items.add(Button.CLEAR);
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new NumberPadRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new GridLayout(this, items);
        }
        return layout;
    }
}
