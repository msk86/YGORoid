package android.ygo.layout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.core.Card;
import android.ygo.utils.Utils;

import java.util.List;

public class GridLayout {
    List<Card> cards;

    int row;
    int col;

    int cardPaddingW;
    int cardPaddingH;
    int maxWidth;

    int realWidth;
    int realHeight;

    public GridLayout(List<Card> cards, int maxWidth, int row) {
        this.cards = cards;
        this.maxWidth = maxWidth;
        this.row = row;
    }

    public void fixPosition() {
        int maxPadding = Utils.cardWidth() / 10;
        int minCol = (maxWidth + maxPadding) / (Utils.cardWidth() + maxPadding);
        col = (int)Math.ceil(1.0 * cards.size() / row);
        this.col = col > minCol ? col : minCol;
        cardPaddingW = (maxWidth - Utils.cardWidth()) / (col - 1) - Utils.cardWidth();
        cardPaddingH = 3;
        realWidth = col * Utils.cardWidth() + (col - 1) * cardPaddingW + 1;
        realHeight = row * Utils.cardHeight() + (row - 1) * cardPaddingH + 1;
    }

    public Bitmap toBitmap(){
        fixPosition();
        Bitmap bmp = Bitmap.createBitmap(realWidth, realHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        int posX = 0;
        int posY = 0;
        for(int r = 0; r < row; r++) {
            posX = 0;
            for (int c = 0; c < col; c++) {
                int index = r * col + c;
                if(index < cards.size()) {
                    Card card = cards.get(index);
                    Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, posX, posY);
                    posX += Utils.cardWidth() + cardPaddingW;
                }
            }
            posY += Utils.cardHeight() + cardPaddingH;
        }
        return bmp;
    }

    public Card cardAt(int x, int y) {
        fixPosition();
        if(x < 0 || x >= realWidth || y < 0 || y>= realHeight) {
            return null;
        }

        int indexX = x / (Utils.cardWidth() + cardPaddingW);
        if(indexX >= cards.size()) {
            return null;
        }
        int indexY = y / (Utils.cardHeight() + cardPaddingH);

        int index = indexY * col + indexX;
        if(index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }
}
