package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.*;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.size.OtherSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.Style;

public class CardSelectorRenderer implements Renderer {
    CardSelector cardSelector;

    public CardSelectorRenderer(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
    }

    @Override
    public Size size() {
        return OtherSize.CARD_SELECTOR;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawBackground(canvas, x, y);
        drawCards(canvas, x, y);
    }

    private void drawCards(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        CardList cardList = cardSelector.getCardList();
        for(Card card: cardList.getCards()) {
            Point pos = cardSelector.getLayout().itemPosition(card);
            Bitmap cardBmp;
            if(cardList.isOpen() && !card.isOpen()) {
                cardBmp = CardCover.getInstance().getBmpGenerator().generate(card.getRenderer().size());
            } else {
                cardBmp = card.getBmpGenerator().generate(card.getRenderer().size());
            }
            canvas.drawBitmap(cardBmp, pos.x, pos.y, new Paint());

            if(card.isSelect()) {
                HighLight highLight = new HighLight(card);
                highLight.getRenderer().draw(canvas, pos.x, pos.y);
            }
        }
    }


    private void drawBackground(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        paint.setColor(Style.windowBackgroundColor());
        paint.setAlpha(150);

        canvas.save();
        canvas.translate(x, y);
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);
        canvas.restore();
    }
}
