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

    public CardList getMaterials() {
        return materials;
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

    public int materialCount() {
        return materials.cards.size();
    }

    private int totalCard() {
        int count = materials.cards.size();
        if (xyzMonster != null) {
            count++;
        }
        return count;
    }

    public Card topCard() {
        if (xyzMonster != null) {
            return xyzMonster;
        }
        if (materials.cards.size() > 0) {
            return materials.cards.get(0);
        }
        return null;
    }

    public Card removeTopCard() {
        Card card = null;
        if(xyzMonster != null) {
            card = xyzMonster;
            xyzMonster = null;
        } else if(materials.cards.size() > 0) {
            card = materials.cards.remove(0);
        }
        return card;
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

//    private Bitmap highLight() {
//        int height = Utils.cardHeight();
//        int width = height;
//        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(highLightBmp);
//        canvas.drawColor(Color.TRANSPARENT);
//        Paint paint = new Paint();
//        paint.setColor(Configuration.highlightColor());
//        paint.setStrokeWidth(4);
//        canvas.drawLine(0, 0, width, 0, paint);
//        canvas.drawLine(width, 0, width, height, paint);
//        canvas.drawLine(width, height, 0, height, paint);
//        canvas.drawLine(0, height, 0, 0, paint);
//        return highLightBmp;
//    }

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
//        if(selected) {
//            Bitmap highLight = highLight();
//            Utils.drawBitmapOnCanvas(canvas, highLight, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
//        }
        return overlayBmp;
    }

    @Override
    public void select() {
        Card topCard = topCard();
        if(topCard != null) {
//            if(!topCard.isSelect()) {
//                topCard.select();
//                selected = false;
//            } else {
//                topCard.unSelect();
//                selected = true;
//            }
            topCard.select();
        }
        selected = true;
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
//        Card topCard = topCard();
//        if(topCard != null) {
//            return topCard.isSelect() || selected;
//        }
        return selected;
    }
}
