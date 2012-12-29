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
import java.util.Random;

public class CardList implements SelectableItem {
    List<Card> cards = new ArrayList<Card>();
    boolean open = true;

    public CardList() {
        List<Card> cards = new ArrayList<Card>();
    }

    public CardList(List<Card> cards) {
        this.cards = cards;
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

    public void push(CardList list) {
        for(Card card : list.cards) {
            this.push(card);
        }
        list.cards.clear();
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


    @Override
    public Bitmap highLight() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
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
        return deckBmp;
    }
}
