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
        if(overRay.getOverRayCards().topCard().isPositive()) {
            return sizePositive();
        } else {
            return sizeNegative();
        }
    }

    private Size sizePositive() {
        return CardSize.NORMAL;
    }
    private Size sizeNegative() {
        return CardSize.NORMAL_NEGATIVE;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;

        drawOverRayUnits(canvas, x, y);

        overRay.getOverRayCards().topCard().getRenderer().draw(canvas, x, y);
    }

    private void drawOverRayUnits(Canvas canvas, int x, int y) {
        if(overRay.getOverRayCards().size() <= 1) {
            return;
        }

        Card topCard = overRay.getOverRayCards().topCard();

        canvas.save();
        canvas.translate(x, y);
        for(int i=Math.min(overRay.getOverRayCards().size()-1, maxVisibleUnits()); i>=1; i--) {
            Card unit = overRay.getOverRayCards().getCards().get(i);
            if(topCard.isPositive()) {
                unit.getRenderer().draw(canvas, overRayOffset() * i, 0);
            } else {
                int offset = (size().width() - size().height()) / 2;
                unit.getRenderer().draw(canvas, offset + overRayOffset() * i, -offset);
            }

        }
        canvas.restore();
    }

    private int overRayOffset() {
        return Math.abs((size().height() - size().width()) / 2 / maxVisibleUnits());
    }

    private int maxVisibleUnits() {
        return 3;
    }
}
