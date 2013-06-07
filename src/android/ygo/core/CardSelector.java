package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.layout.GridLayout;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class CardSelector implements Item {
    SelectableItem sourceItem;
    CardList cardList;
    GridLayout layout;

    public CardSelector(SelectableItem sourceItem, CardList cardList) {
        this.sourceItem = sourceItem;
        this.cardList = cardList;
        layout = new GridLayout(cardList.cards, Utils.totalWidth(), 4);
    }

    public SelectableItem getSourceItem() {
        return sourceItem;
    }

    public CardList getCardList() {
        return cardList;
    }

    public Card cardAt(int x, int y) {
        return layout.cardAt(x, y);
    }

    public Card remove(Card card) {
        return cardList.remove(card);
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap background = background();

        Bitmap bmp = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        Utils.drawBitmapOnCanvas(canvas, background, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

        Bitmap cardsBmp = layout.toBitmap();
        Utils.drawBitmapOnCanvas(canvas, cardsBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
        cardsBmp.recycle();

        return bmp;
    }

    public Bitmap background() {
        int width = Utils.totalWidth();
        int height = Utils.screenHeight();
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        canvas.drawColor(Configuration.cardSelectorBackgroundColor());
        return background;
    }
}
