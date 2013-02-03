package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.layout.GridLayout;
import android.ygo.layout.LinerLayout;
import android.ygo.utils.Utils;

import java.util.List;

public class CardSelector implements Item {
    SelectableItem sourceItem;
    CardList cardList;
    GridLayout layout;

    public CardSelector(SelectableItem sourceItem, CardList cardList) {
        this.sourceItem = sourceItem;
        this.cardList = cardList;
        layout = new GridLayout(cardList.cards, Utils.unitLength()*6, 4);
    }

    public SelectableItem getSourceItem() {
        return sourceItem;
    }

    public CardList getCardList() {
        return cardList;
    }

    public Card cardAt(int x, int y) {
        if(cardList.size() == 0) {
            return null;
        }
        return cardList.getCards().get(0);
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

//        int x = border;
//        int y = border;
//        for(int i=page * pageLimit;i<cardList.size();i++) {
//            if(i == (page + 1) * pageLimit) {
//                break;
//            }
//            Card card = cardList.cards.get(i);
//            if(i != 0 && i % 7 == 0) {
//                x = border;
//                y += Utils.cardHeight() + cardPadding;
//            }
//            x += cardPadding;
//
//            card.open();
//            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, x, y);
//            card.set();
//
//            x += Utils.cardWidth();
//        }

        return bmp;
    }

    public Bitmap background() {
        int width = Utils.unitLength() * 6;
        int height = Utils.screenHeight();
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        canvas.drawColor(Color.GRAY);
        return background;
    }
}
