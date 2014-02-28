package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Point;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.HandCards;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.size.TotalSize;

public class HandCardsRenderer implements Renderer {
    HandCards handCards;

    public HandCardsRenderer(HandCards handCards) {
        this.handCards = handCards;
    }

    @Override
    public Size size() {
        return TotalSize.HAND_CARDS;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        for(Card card : handCards.getCardList().getCards()) {
            Point pos = handCards.getLayout().itemPosition(card);
            card.getRenderer().draw(canvas, pos.x, pos.y + selectCardPaddingY(card));
        }

        canvas.restore();
    }

    private int selectCardPaddingY(Card card) {
        if(card.isSelect()) {
            return 0;
        } else {
            return card.getRenderer().size().height() / 7;
        }
    }
}
