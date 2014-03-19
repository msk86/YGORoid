package org.msk86.ygoroid.newcore.impl.builder.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.newcore.impl.builder.SideDeckSection;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.BuilderSize;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

public class SideDeckSectionRenderer implements Renderer {
    SideDeckSection sideDeckSection;

    public SideDeckSectionRenderer(SideDeckSection sideDeckSection) {
        this.sideDeckSection = sideDeckSection;
    }

    @Override
    public Size size() {
        return new Size(sideDeckSection.getHolder().getRenderer().size().width(), BuilderSize.SIDE_SECTION.height());
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawItems(canvas, x, y);
        drawFrame(canvas, x, y);
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        if(!sideDeckSection.isHighLight()) return;
        canvas.save();
        canvas.translate(x, y);
        Paint paint = new Paint();
        paint.setColor(Style.fontColor());
        paint.setAlpha(40);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);
        canvas.restore();
    }

    private void drawItems(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        for (Item item : sideDeckSection.getLayout().items()) {
            Card card = (Card) item;
            Point pos = sideDeckSection.getLayout().itemPosition(card);
            canvas.drawBitmap(card.getBmpGenerator().generate(CardSize.SIDING), pos.x, pos.y, new Paint());
            if(card.isSelect()) {
                HighLight highLight = new HighLight(card);
                highLight.setSize(CardSize.SIDING);
                highLight.getRenderer().draw(canvas, pos.x, pos.y);
            }
        }

        canvas.restore();
    }
}
