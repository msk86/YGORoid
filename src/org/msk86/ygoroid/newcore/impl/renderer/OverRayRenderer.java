package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

public class OverRayRenderer implements Renderer {
    private OverRay overRay;
    int x, y;

    public OverRayRenderer(OverRay overRay) {
        this.overRay = overRay;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public Size size() {
        return CardSize.NORMAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;

        drawOverRayUnits(canvas, x, y);

        canvas.save();
        int offset = (size().height() - size().width()) / 2;
        canvas.translate(x, y);
        Card topCard = overRay.getOverRayCards().topCard();
        if(topCard.isPositive()) {
            topCard.getRenderer().draw(canvas, offset, 0);
        } else {
            topCard.getRenderer().draw(canvas, 0, offset);
        }
        canvas.restore();
    }

    private void drawOverRayUnits(Canvas canvas, int x, int y) {
        if(overRay.getOverRayCards().size() <= 1) {
            return;
        }

        canvas.save();
        int offset = (size().height() - size().width()) / 2;
        canvas.translate(x, y);
        for(int i=1; i<= Math.min(overRay.getOverRayCards().size()-1, maxVisibleUnits()); i++) {
            Card unit = overRay.getOverRayCards().getCards().get(i);
            unit.getRenderer().draw(canvas, offset + overRayOffset() * i, 0);
        }
        canvas.restore();
    }

    private int overRayOffset() {
        return (size().height() - size().width()) / 2 / maxVisibleUnits();
    }

    private int maxVisibleUnits() {
        return 3;
    }
}
