package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.size.FieldSize;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class DuelRenderer implements Renderer {
    private Duel duel;

    public DuelRenderer(Duel duel) {
        this.duel = duel;
        initLayout();
    }

    private void initLayout() {
        AbsoluteLayout layout = (AbsoluteLayout) duel.getLayout();

        layout.addItem(duel.getDuelFields(), 0, 0, 0);
        layout.addItem(duel.getHandCards(), (size().width() - duel.getHandCards().getRenderer().size().width()) / 2, FieldSize.RECT.height() * 3, 0);

        layout.addItem(duel.getLifePoint(), FieldSize.RECT.width() + padding(), 0, 1);
        layout.addItem(duel.getCoin(), FieldSize.RECT.width() + duel.getLifePoint().getRenderer().size().width() + padding() * 2, (FieldSize.RECT.height() - duel.getCoin().getRenderer().size().height()) / 2, 1);
        layout.addItem(duel.getDice(), FieldSize.RECT.width() + duel.getLifePoint().getRenderer().size().width() + padding() * 3 + duel.getCoin().getRenderer().size().width(), (FieldSize.RECT.height() - duel.getDice().getRenderer().size().height()) / 2, 1);
        layout.addItem(duel.getInfoBar(), (size().width() - duel.getHandCards().getRenderer().size().width()) / 2, size().height() - duel.getInfoBar().getRenderer().size().height(), 3);
    }

    private void updateLayoutWithWindow() {
        AbsoluteLayout layout = (AbsoluteLayout) duel.getLayout();

        if(duel.getCardSelector() != null) {
            layout.addItem(duel.getCardSelector(), 0, 0, 2);
        } else {
            layout.removeItem(duel.getCardSelector());
        }

        if(duel.getCardEffectWindow() != null) {
            layout.addItem(duel.getCardEffectWindow(), 0, 0, 4);
        }  else {
            layout.removeItem(duel.getCardEffectWindow());
        }
        if(duel.getLifePointCalculator() != null) {
            layout.addItem(duel.getLifePointCalculator(), 0, 0, 4);
        }  else {
            layout.removeItem(duel.getLifePointCalculator());
        }
    }

    @Override
    public Size size() {
        return OtherSize.TOTAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        updateLayoutWithWindow();

        Layout layout = duel.getLayout();
        for(Item item : layout.items()) {
            Point pos = layout.itemPosition(item);
            item.getRenderer().draw(canvas, pos.x, pos.y);
        }
    }

    private int padding() {
        return Style.padding() * 5;
    }
}
