package org.msk86.ygoroid.newcore.impl.side.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.newcore.impl.side.ExDeckSection;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.SideChangerSize;
import org.msk86.ygoroid.size.Size;

public class ExDeckSectionRenderer implements Renderer {
    ExDeckSection exDeckSection;

    public ExDeckSectionRenderer(ExDeckSection exDeckSection) {
        this.exDeckSection = exDeckSection;
    }

    @Override
    public Size size() {
        return SideChangerSize.EX_SECTION;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        for (Item item : exDeckSection.getLayout().items()) {
            Card card = (Card) item;
            Point pos = exDeckSection.getLayout().itemPosition(card);
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
