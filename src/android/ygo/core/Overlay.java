package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Overlay implements SelectableItem {
    Card xyzMonster;
    CardList materials;

    public Overlay(Card card) {
        materials = new CardList();
        overlay(card);
    }

    public void overlay(Card card) {
        if (card.type == CardType.XYZ_MONSTER) {
            if (xyzMonster != null) {
                materials.push(xyzMonster);
            }
            xyzMonster = card;
        } else {
            materials.push(card);
        }
    }

    private int totalCard() {
        int count = materials.cards.size();
        if(xyzMonster != null) {
            count ++;
        }
        return count;
    }

    private Card topCard() {
        if(xyzMonster != null) {
            return xyzMonster;
        }
        if(materials.cards.size() > 0) {
            return materials.cards.get(0);
        }
        return null;
    }


    @Override
    public Bitmap highLight() {
        Card top = topCard();
        if(top != null) {
            return top.highLight();
        }
        return Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888);
    }

    @Override
    public Bitmap toBitmap() {
        int overlayOffset = Utils.cardWidth() / 15;
        int width = Utils.cardWidth() + (totalCard() - 1) * overlayOffset;
        Bitmap overlayBmp = Bitmap.createBitmap(width, Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlayBmp);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        canvas.translate((totalCard()- 1) * overlayOffset, 0);
        for (int i = materials.getCards().size() - 1; i >= 0; i--) {
            Card card = materials.getCards().get(i);
            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
            canvas.translate(-overlayOffset, 0);
        }
        if(xyzMonster != null) {
            Utils.drawBitmapOnCanvas(canvas, xyzMonster.toBitmap(), paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
        }
        return overlayBmp;
    }
}
