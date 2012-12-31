package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CardList implements SelectableItem {
    private boolean selected = false;
    List<Card> cards = new ArrayList<Card>();
    boolean open = true;

    public CardList() {
        cards = new ArrayList<Card>();
    }

    public CardList(boolean open) {
        this();
        this.open = open;
    }

    public CardList(List<Card> cards) {
        this(cards, true);
    }

    public CardList(List<Card> cards, boolean open) {
        this(open);
        for(Card card : cards) {
            if(open) {
                card.open();
            } else {
                card.set();
            }
            this.push(card);
        }
    }

    public Card pop() {
        return cards.remove(0);
    }

    public void push(Card card) {
        if(open) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        cards.add(0, card);
    }

    public void push(List<Card> cards) {
        for(Card card : cards) {
            this.push(card);
        }
    }

    public void push(CardList list) {
        this.push(list.cards);
    }


    public void unShift(Card card) {
        if(open) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    private Bitmap highLight() {
        int width = Utils.cardWidth();
        int height = Utils.cardHeight();
        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(highLightBmp);
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(width, height, 0, height, paint);
        canvas.drawLine(0, height, 0, 0, paint);
        return highLightBmp;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap deckBmp;
        if(cards.size() > 0) {
            deckBmp = cards.get(0).toBitmap();
        } else {
            deckBmp = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(deckBmp);

        CharSequence cs = "" + cards.size();
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Configuration.fontColor());

        canvas.translate(0, Utils.cardHeight() - 20);
        StaticLayout layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0,0,false);
        layout.draw(canvas);
        canvas.translate(0, 20 - Utils.cardHeight());

        if(selected) {
            Bitmap highLight = highLight();
            Utils.drawBitmapOnCanvas(canvas, highLight, null, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            highLight.recycle();
        }

        return deckBmp;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void unSelect() {
        selected = false;
    }

    @Override
    public boolean isSelect() {
        return selected;
    }
}
