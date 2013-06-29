package android.ygo.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CardList implements SelectableItem, Drawable {
    public static final String DECK = "卡组";
    public static final String EX = "额外";
    public static final String GRAVEYARD = "墓地";
    public static final String REMOVED = "除外";
    public static final String TEMPORARY = "临时";

    private boolean selected = false;

    private boolean storeToken = false;

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

    public Card topCard() {
        if (cards.size() > 0) {
            return cards.get(0);
        }
        return null;
    }

    public Card pop() {
        if (cards.size() > 0) {
            return cards.remove(0);
        }
        return null;
    }

    public List<Card> pop(int size) {
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < size; i++) {
            Card card = pop();
            if (card != null) {
                cards.add(card);
            }
        }
        return cards;
    }

    public Card remove(Card card) {
        if (cards.remove(card)) {
            return card;
        }
        return null;
    }

    public void push(Card card) {
        push(card, false);
    }

    public void push(Card card, boolean forceSet) {
        add(card, 0, forceSet);
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
        add(card, cards.size(), false);
    }

    private void add(Card card, int index, boolean forceSet) {
        if (card == null) {
            return;
        }
        if (!storeToken && card.isToken()) {
            return;
        }
        if (!forceSet && open) {
            card.open();
        } else {
            card.set();
        }
        card.positive();
        card.unSelect();
        card.clearIndicator();
        cards.add(index, card);
    }

    public boolean isOpen() {
        return open;
    }

    public void openAll() {
        for (Card card : cards) {
            card.open();
        }
    }

    public void setAll() {
        for (Card card : cards) {
            card.set();
        }
    }

    public void shuffle() {
        Utils.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        if (cards.size() > 0) {
            if(!open) {
                helper.drawBitmap(canvas, Card.CARD_PROTECTOR, helper.center(width(), Card.CARD_PROTECTOR.getWidth()), 0, new Paint());
            } else {
                Card card = cards.get(0);
                helper.drawDrawable(canvas, card, helper.center(width(), card.width()), 0);
            }
        }

        drawText(canvas, x, y);

        if (selected) {
            drawHighLight(canvas, x, y);
        }
    }

    private void drawText(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(Utils.unitLength() / 9);
        textPaint.setColor(Configuration.fontColor());
        textPaint.setShadowLayer(1, 0, 0, Configuration.textShadowColor());
        textPaint.setUnderlineText(true);

        StaticLayout layout = new StaticLayout(name, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        helper.drawLayout(canvas, layout, 0, Utils.cardHeight() * 3 / 4);

        layout = new StaticLayout(String.valueOf(cards.size()), textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        textPaint.setUnderlineText(false);
        helper.drawLayout(canvas, layout, 0, Utils.cardHeight() * 7 / 8);
    }

    private void drawHighLight(Canvas canvas, int x, int y) {
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        helper.drawRect(canvas, new Rect(0, 0, width(), height()), paint);
    }

    @Override
    public int width() {
        return Utils.cardWidth();
    }

    @Override
    public int height() {
        return Utils.cardHeight();
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void unSelect() {
        selected = false;
        if(topCard() != null) {
            topCard().unSelect();
        }
    }

    @Override
    public boolean isSelect() {
        return selected;
    }

    public void setStoreToken(boolean storeToken) {
        this.storeToken = storeToken;
    }
}
