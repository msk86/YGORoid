package org.msk86.ygoroid.newcore.impl.builder.renderer;

import android.graphics.Canvas;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.size.Size;

public class DeckBuilderRenderer implements Renderer {
    DeckBuilder builder;

    public DeckBuilderRenderer(DeckBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Size size() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
