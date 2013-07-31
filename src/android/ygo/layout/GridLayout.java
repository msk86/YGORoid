package android.ygo.layout;

import android.graphics.Canvas;
import android.ygo.core.Card;
import android.ygo.core.Drawable;
import android.ygo.core.Item;
import android.ygo.utils.Utils;

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
        this.cards = cards;
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

        int posX = 0;
        int posY = 0;
        for (int r = 0; r < row; r++) {
            posX =  - (unitHeight - unitWidth) / 2 + 1;
            for (int c = 0; c < col; c++) {
                int index = r * col + c;
                if (index < cards.size()) {
                    helper.drawDrawable(canvas, cards.get(index), posX, posY);
                    posX += unitWidth + cardPaddingW;
                }
            }
            posY += unitHeight + cardPaddingH;
        }
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
}
