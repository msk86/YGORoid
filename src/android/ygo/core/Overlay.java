package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Overlay implements SelectableItem {
    private boolean selected = false;

    Card xyzMonster;
    CardList materials;

    public Overlay(Card card) {
        materials = new CardList();
        overlay(card);
    }

    public Card getXyzMonster() {
        return xyzMonster;
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
        if (xyzMonster != null) {
            count++;
        }
        return count;
    }

    private Card topCard() {
        if (xyzMonster != null) {
            return xyzMonster;
        }
        if (materials.cards.size() > 0) {
            return materials.cards.get(0);
        }
        return null;
    }

    private Bitmap materialsBmp() {
        int materialOffset = materials.cards.size() - 1;
        int overlayOffset = Utils.cardWidth() / 15;
        int materialWidth = Utils.cardWidth() + materialOffset * overlayOffset;
        int height = Utils.cardHeight();
        Bitmap materialsBmp = Bitmap.createBitmap(materialWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(materialsBmp);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        canvas.translate(materialOffset * overlayOffset, 0);
        for (int i = materials.getCards().size() - 1; i >= 0; i--) {
            Card card = materials.getCards().get(i);
            Utils.drawBitmapOnCanvas(canvas, card.toBitmap(), paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
            canvas.translate(-overlayOffset, 0);
        }
        return materialsBmp;
    }

    @Override
    public Bitmap toBitmap() {
        int overlayOffset = Utils.cardWidth() / 15;
        int height = Utils.cardHeight();
        int width = height;
        Bitmap overlayBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlayBmp);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        Bitmap materialsBmp = materialsBmp();

        int materialX = (width - Utils.cardWidth()) / 2;
        if (xyzMonster != null) {
            materialX += overlayOffset;
        }
        Utils.drawBitmapOnCanvas(canvas, materialsBmp, paint, materialX, Utils.DRAW_POSITION_CENTER);
        materialsBmp.recycle();

        if (xyzMonster != null) {
            Utils.drawBitmapOnCanvas(canvas, xyzMonster.toBitmap(), paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
        }
        return overlayBmp;
    }

    @Override
    public void select() {
        selected = true;
        Card topCard = topCard();
        if (topCard != null) {
            topCard.select();
        }
    }

    @Override
    public void unSelect() {
        selected = false;
        Card topCard = topCard();
        if (topCard != null) {
            topCard.unSelect();
        }
    }

    @Override
    public boolean isSelect() {
        return selected;
    }
}
