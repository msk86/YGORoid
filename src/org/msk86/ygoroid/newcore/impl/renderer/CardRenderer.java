package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardCover;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

public class CardRenderer implements Renderer {
    Card card;
    int x, y;
    Size size;
    HighLight highLight;


    public CardRenderer(Card card) {
        this.card = card;
        size = CardSize.NORMAL;
        highLight = new HighLight(card);
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
        return size;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        this.x = x;
        this.y = y;
        Bitmap cardBmp = getCardBmp();

        drawCardBmp(canvas, cardBmp, x, y);

        drawIndicator(canvas, x, y);

        drawHighLight(canvas, x, y);
    }

    private Bitmap getCardBmp() {
        if (card.isOpen()) {
            return card.getBmpGenerator().generate(size);
        } else {
            return CardCover.getInstance().getBmpGenerator().generate(size);
        }
    }

    private void drawCardBmp(Canvas canvas, Bitmap cardBmp, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        x = y = 0;
        if (!card.isPositive()) {
            canvas.rotate(-90);
            x = -size.width();
        }
        canvas.drawBitmap(cardBmp, x, y, new Paint());
        canvas.restore();
    }

    private void drawIndicator(Canvas canvas, int x, int y) {
        card.getIndicator().getRenderer().draw(canvas, x, y);
    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);
        x = y = 0;
        if (!card.isPositive()) {
            canvas.rotate(-90);
            x = -size.width();
        }
        highLight.getRenderer().draw(canvas, x, y);
        canvas.restore();
    }
}
