package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class OverRay implements SelectableItem {
    private boolean selected = false;

    Card topCard;
    CardList overRayUnits;

    public OverRay(Card card) {
        overRayUnits = new CardList("OVERRAY");
        overRay(card);
    }

    public CardList getOverRayUnits() {
        return overRayUnits;
    }

    public void overRay(Card card) {
        if (topCard == null) {
            topCard = card;
        } else {
            if (topCard.getSubTypes().contains(CardSubType.XYZ)
                    && !card.getSubTypes().contains(CardSubType.XYZ)) {
                overRayUnits.push(card);
            } else {
                overRayUnits.push(topCard);
                topCard = card;
            }
        }
        topCard.open();
    }

    public int totalCard() {
        int count = overRayUnits.cards.size();
        if (topCard != null) {
            count++;
        }
        return count;
    }

    public Card topCard() {
        return topCard;
    }

    public Card removeTopCard() {
        Card top = topCard;
        Card card = overRayUnits.pop();
        topCard = card;
        return top;
    }

    public void adjust(Field field) {
        if (totalCard() == 1) {
            field.removeItem();
            field.setItem(topCard);
        }
    }

    private Bitmap unitsBmp() {
        int unitOffset = overRayUnits.cards.size() - 1;
        int overRayOffset = Utils.cardWidth() / 15;
        int unitWidth = Utils.cardWidth() + unitOffset * overRayOffset;
        int height = Utils.cardHeight();
        Bitmap unitsBmp = Bitmap.createBitmap(unitWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(unitsBmp);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        canvas.translate(unitOffset * overRayOffset, 0);
        for (int i = overRayUnits.getCards().size() - 1; i >= 0; i--) {
            Bitmap bitmap = overRayUnits.getCards().get(i).toBitmap();
            Utils.drawBitmapOnCanvas(canvas, bitmap, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
            bitmap.recycle();
            canvas.translate(-overRayOffset, 0);
        }
        return unitsBmp;
    }

    @Override
    public Bitmap toBitmap() {
        int overRayOffset = Utils.cardWidth() / 15;
        int height = Utils.cardHeight();
        int width = height;
        Bitmap overRayBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overRayBmp);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        Bitmap unitsBmp = unitsBmp();

        int unitX = (width - Utils.cardWidth()) / 2;
        if (topCard != null) {
            unitX += overRayOffset;
        }
        Utils.drawBitmapOnCanvas(canvas, unitsBmp, paint, unitX, Utils.DRAW_POSITION_CENTER);
        unitsBmp.recycle();

        if (topCard != null) {
            Bitmap topCardBmp = topCard.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, topCardBmp, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_CENTER);
            topCardBmp.recycle();
        }
        return overRayBmp;
    }

    @Override
    public void select() {
        Card topCard = topCard();
        if (topCard != null) {
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
        return selected;
    }
}
