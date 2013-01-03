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

    String name;

    List<Card> cards = new ArrayList<Card>();
    boolean open = true;

    public CardList() {
        cards = new ArrayList<Card>();
        this.name = "";
    }

    public CardList(String name) {
        this();
        this.name = name;
    }

    public CardList(String name, boolean open) {
        this(name);
        this.open = open;
    }

    public CardList(String name, List<Card> cards, boolean open) {
        this(name, open);
        for (Card card : cards) {
            if (open) {
                card.open();
            } else {
                card.set();
            }
            this.push(card);
        }
    }

    public String getName() {
        return name;
    }

    public int size() {
        return cards.size();
    }


    public Card pop() {
        if(cards.size() > 0) {
            return cards.remove(0);
        }
        return null;
    }

    public Card remove(Card card) {
        if (cards.remove(card)) {
            return card;
        }
        return null;
    }

    public void push(Card card) {
        if(card == null) {
            return;
        }
        if (open) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        card.unSelect();
        cards.add(0, card);
    }

    public void push(List<Card> cards) {
        for (Card card : cards) {
            this.push(card);
        }
    }

    public void push(CardList list) {
        this.push(list.cards);
    }


    public void unShift(Card card) {
        if (open) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        card.unSelect();
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
        if (cards.size() > 0) {
            deckBmp = cards.get(0).toBitmap();
        } else {
            deckBmp = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(deckBmp);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(Utils.unitLength() / 10);
        textPaint.setColor(Configuration.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Color.BLACK);
        textPaint.setUnderlineText(true);

        canvas.translate(0, 5);
        CharSequence cs = name;
        StaticLayout layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0, 0, false);
        layout.draw(canvas);
        canvas.translate(0, -5);

        textPaint.setUnderlineText(false);
        canvas.translate(0, Utils.cardHeight() - 20);
        cs = "" + cards.size();
        layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0, 0, false);
        layout.draw(canvas);

        canvas.translate(0, 20 - Utils.cardHeight());

        if (selected) {
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
