package org.msk86.ygoroid.newcore.impl.builder.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.BuilderSize;
import org.msk86.ygoroid.size.InfoBarSize;
import org.msk86.ygoroid.size.Size;

public class DeckBuilderRenderer implements Renderer {
    DeckBuilder builder;

    public DeckBuilderRenderer(DeckBuilder builder) {
        this.builder = builder;
        initLayout();
    }

    private void initLayout() {
        AbsoluteLayout layout = (AbsoluteLayout) builder.getLayout();

        layout.addItem(builder.getMainDeckSection(), 0, 0, 0);
        layout.addItem(builder.getExDeckSection(), 0, BuilderSize.MAIN_SECTION.height() + Style.padding(), 0);
        layout.addItem(builder.getSideDeckSection(), 0, BuilderSize.MAIN_SECTION.height() + BuilderSize.EX_SECTION.height() + Style.padding() * 2, 0);
        layout.addItem(builder.getInfoBar(), 0, size().height() - InfoBarSize.INFO_BAR.height(), 1);
    }

    private void updateLayoutWithWindow() {
        AbsoluteLayout layout = (AbsoluteLayout) builder.getLayout();

        if(builder.getCardEffectWindow() != null) {
            layout.addItem(builder.getCardEffectWindow(), 0, 0, 2);
        }  else {
            layout.removeItems(CardEffectWindow.class);
        }
    }

    @Override
    public Size size() {
        return BuilderSize.BUILDER;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        updateLayoutWithWindow();

        canvas.save();
        canvas.translate(x, y);

        for(Item item : builder.getLayout().items()) {
            Point point = builder.getLayout().itemPosition(item);
            item.getRenderer().draw(canvas, point.x, point.y);
        }

        canvas.restore();
    }
}
