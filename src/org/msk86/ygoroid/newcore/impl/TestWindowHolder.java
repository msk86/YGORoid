package org.msk86.ygoroid.newcore.impl;

import android.graphics.Canvas;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;

public class TestWindowHolder implements Item {
    @Override
    public Renderer getRenderer() {
        return new Renderer() {
            @Override
            public Size size() {
                return OtherSize.CARD_SELECTOR;
            }

            @Override
            public void draw(Canvas canvas, int x, int y) {

            }
        };
    }
}
