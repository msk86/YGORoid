package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newcore.impl.HighLight;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

public class DeckRenderer implements Renderer {
    Deck deck;
    HighLight highLight;

    public DeckRenderer(Deck deck) {
        this.deck = deck;
        highLight = new HighLight(deck);
    }

    @Override
    public Size size() {
        return CardSize.NORMAL;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        if(deck.getCardList().size() > 0) {
            Card topCard = deck.getCardList().topCard();
            topCard.getRenderer().draw(canvas, x, y);
        }

        drawText(canvas, x, y);
        drawHighLight(canvas, x, y);
    }

    private void drawText(Canvas canvas, int x, int y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(size().width() / 6);
        textPaint.setColor(Style.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Style.textShadowColor());
        textPaint.setUnderlineText(true);

        StaticLayout layout = new StaticLayout(deck.getName(), textPaint, size().width(), Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        canvas.save();
        canvas.translate(x, y + size().height() * 3 / 4);
        layout.draw(canvas);
        canvas.restore();

        layout = new StaticLayout(String.valueOf(deck.getCardList().size()), textPaint, size().width(), Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        textPaint.setUnderlineText(false);

        canvas.save();
        canvas.translate(x, y + size().height() * 7 / 8);
        layout.draw(canvas);
        canvas.restore();
    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        highLight.getRenderer().draw(canvas, x, y);
    }
}
