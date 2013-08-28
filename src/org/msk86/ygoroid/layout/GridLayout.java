package org.msk86.ygoroid.layout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Drawable;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class GridLayout implements Layout, Drawable {
    List<Card> cards;

    int row;
    int col;

    int cardPaddingW;
    int cardPaddingH;
    int maxWidth;
    private int unitWidth;
    private int unitHeight;

    public GridLayout(List<Card> cards, int maxWidth, int row) {
        this(cards, maxWidth, row, Utils.cardWidth(), Utils.cardHeight());
    }

    public GridLayout(List<Card> cards, int maxWidth, int row, int unitWidth, int unitHeight) {
        if (cards != null) {
            this.cards = cards;
        } else {
            this.cards = new ArrayList<Card>();
        }
        this.maxWidth = maxWidth;
        this.row = row;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
    }

    public void fixPosition() {
        int maxPadding = unitWidth / 10;
        int minCol = (maxWidth + maxPadding) / (unitWidth + maxPadding);
        col = (int) Math.ceil(1.0 * cards.size() / row);
        this.col = col > minCol ? col : minCol;
        cardPaddingW = (maxWidth - unitWidth - 1) / (col - 1) - unitWidth;
        cardPaddingH = 3;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        fixPosition();
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        for (int r = 0, posY = 0, posX; r < row; r++) {
            posX = 1;
            for (int c = 0; c < col; c++) {
                int index = r * col + c;
                if (index < cards.size()) {
                    Card card = cards().get(index);
                    if (card.isOpen()) {
                        helper.drawBitmap(canvas, card.getBmpCache().get(unitWidth, unitHeight), posX, posY, new Paint());
                    } else {
                        helper.drawBitmap(canvas, Card.CARD_PROTECTOR, posX, posY, new Paint());
                    }
                    if (card.isSelect()) {
                        drawHighLight(canvas, x + posX, y + posY);
                    }
                    posX += unitWidth + cardPaddingW;
                }
            }
            posY += unitHeight + cardPaddingH;
        }
    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Rect highLightRect = new Rect(0, 0, unitWidth, unitHeight);
        helper.drawRect(canvas, highLightRect, paint);
    }

    @Override
    public int width() {
        return col * unitWidth + (col - 1) * cardPaddingW + 1;
    }

    @Override
    public int height() {
        return row * unitHeight + (row - 1) * cardPaddingH + 1;
    }

    @Override
    public Card cardAt(int x, int y) {
        fixPosition();
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            return null;
        }

        int indexX = x / (unitWidth + cardPaddingW);
        if (indexX >= cards.size()) {
            return null;
        }
        int indexY = y / (unitHeight + cardPaddingH);

        int index = indexY * col + indexX;
        if (index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }

    @Override
    public List<Card> cards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
