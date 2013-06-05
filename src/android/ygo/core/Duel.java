package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.op.Drag;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Duel implements Item {
    private LifePoint lifePoint;

    private DuelFields duelFields;

    private HandCards handCards;

    private CardSelector cardSelector;

    private InfoWindow window;

    private SelectableItem currentSelectItem;

    private Drag drag;
    private List<Card> mainDeckCards;
    private List<Card> exDeckCards;
    private Item currentSelectItemContainer;

    private Dice dice;
    private Coin coin;

    public Duel() {
        initDuelField();
    }

    public void start(List<Card> mainDeckCards, List<Card> exDeckCards) {
        if (mainDeckCards == null) {
            mainDeckCards = new ArrayList<Card>();
        }
        if (exDeckCards == null) {
            exDeckCards = new ArrayList<Card>();
        }
        this.mainDeckCards = mainDeckCards;
        this.exDeckCards = exDeckCards;
        initDeck();
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.shuffle();
        initHandCards();
    }

    public void restart() {
        start(this.mainDeckCards, this.exDeckCards);
    }

    public void initDuelField() {
        lifePoint = new LifePoint();

        duelFields = new DuelFields();
        Deck deck = new Deck("DECK");
        Deck exDeck = new Deck("EX");
        CardList graveyard = new CardList("GRAVEYARD");
        CardList removed = new CardList("REMOVED");
        CardList temp = new CardList("TEMPORARY");
        duelFields.getDeckField().setItem(deck);
        duelFields.getExDeckField().setItem(exDeck);
        duelFields.getGraveyardField().setItem(graveyard);
        duelFields.getRemovedField().setItem(removed);
        duelFields.getTempField().setItem(temp);

        handCards = new HandCards();

        window = new InfoWindow();

        dice = new Dice();
        coin = new Coin();
    }

    private void initDeck() {
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.push(mainDeckCards);

        Deck exDeck = (Deck) duelFields.getExDeckField().getItem();
        exDeck.push(exDeckCards);
    }

    private void initHandCards() {
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        handCards.add(deck.pop(5));
    }

    public LifePoint getLifePoint() {
        return lifePoint;
    }

    public CardSelector getCardSelector() {
        return cardSelector;
    }

    public void setCardSelector(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
    }

    public DuelFields getDuelFields() {
        return duelFields;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    public InfoWindow getInfoWindow() {
        return window;
    }

    public void unSelect() {
        if (currentSelectItem != null) {
            currentSelectItem.unSelect();
            currentSelectItem = null;
            currentSelectItemContainer = null;
        }
        window.clearInfo();
    }

    public void select(SelectableItem item, Item container) {
        if (item != null) {
            if (item != currentSelectItem) {
                unSelect();
            }
            currentSelectItem = item;
            currentSelectItemContainer = container;
            currentSelectItem.select();
        } else {
            unSelect();
        }

        if (item != null) {
            window.setInfo(item);
        }
    }

    public SelectableItem itemAt(int x, int y) {
        if (inLifePoint(x, y)) {
            return lifePoint;
        } else if (inDice(x, y)) {
            return dice;
        } else if (inCoin(x, y)) {
            return coin;
        } else if (inDuelFields(x, y)) {
            return duelFields.itemOnFieldAt(x, y);
        } else if (inHand(x, y)) {
            return handCards.cardAt(x, y);
        } else if (inCardSelector(x, y)) {
            return cardSelector.cardAt(x, y);
        } else if (inInfo(x, y)) {
            return currentSelectItem;
        }
        return null;
    }

    public Item containerAt(int x, int y) {
        if (inLifePoint(x, y)) {
            return null;
        } else if (inDice(x, y)) {
            return null;
        } else if (inCoin(x, y)) {
            return null;
        } else if (inDuelFields(x, y)) {
            return duelFields.fieldAt(x, y);
        } else if (inHand(x, y)) {
            return handCards;
        } else if (inCardSelector(x, y)) {
            return cardSelector.cardList;
        } else if (inInfo(x, y)) {
            return window;
        }
        return null;
    }

    public Field fieldAt(int x, int y) {
        return duelFields.fieldAt(x, y);
    }

    public void setDrag(Drag drag) {
        this.drag = drag;
    }

    public Drag getDrag() {
        return drag;
    }

    public boolean inDice(int x, int y) {
        if (cardSelector != null) {
            return false;
        }
        if (x >= Utils.unitLength() * 2.4 && x < Utils.unitLength() * 2.9) {
            if (y < Utils.unitLength() / 2) {
                return true;
            }
        }
        return false;
    }

    public boolean inCoin(int x, int y) {
        if (cardSelector != null) {
            return false;
        }
        if (x >= Utils.unitLength() * 2.4 && x < Utils.unitLength() * 2.9) {
            if (y >= Utils.unitLength() / 2 && y < Utils.unitLength()) {
                return true;
            }
        }
        return false;
    }

    public boolean inLifePoint(int x, int y) {
        if (cardSelector != null) {
            return false;
        }
        if (x >= Utils.unitLength() && x < Utils.unitLength() * 2.2) {
            if (y < Utils.unitLength()) {
                return true;
            }
        }
        return false;
    }

    public boolean inCardSelector(int x, int y) {
        if (cardSelector == null) {
            return false;
        }
        if (y >= Utils.screenHeight() - Utils.cardHeight() / 6) {
            return false;
        }
        return true;
    }

    public boolean inDuelFields(int x, int y) {
        if (cardSelector != null) {
            return false;
        }
        return y < Utils.unitLength() * 3 && x < Utils.totalWidth();
    }

    public boolean inHand(int x, int y) {
        if (cardSelector != null) {
            return false;
        }
        return y >= Utils.unitLength() * 3 && y < Utils.screenHeight() - Utils.cardHeight() / 6;
    }

    public boolean inInfo(int x, int y) {
        return y >= Utils.screenHeight() - Utils.cardHeight() / 6;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap duelBmp = Bitmap.createBitmap(Utils.totalWidth(), Utils.unitLength() * 4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(duelBmp);
        Paint paint = new Paint();

        Bitmap lpBmp = lifePoint.toBitmap();
        Utils.drawBitmapOnCanvas(canvas, lpBmp, paint, Utils.unitLength(), (Utils.unitLength() - lpBmp.getHeight()) / 2);

        Bitmap diceBmp = dice.toBitmap();
        Utils.drawBitmapOnCanvas(canvas, diceBmp, paint, (int) (Utils.unitLength() * 2.4), 0);

        Bitmap coinBmp = coin.toBitmap();
        Utils.drawBitmapOnCanvas(canvas, coinBmp, paint, (int) (Utils.unitLength() * 2.4), Utils.unitLength() / 2);

        if (cardSelector == null) {
            Bitmap fieldBmp = duelFields.toBitmap();
            Bitmap handBmp = handCards.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, fieldBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            Utils.drawBitmapOnCanvas(canvas, handBmp, paint, Utils.DRAW_POSITION_FIRST, fieldBmp.getHeight() + 1);
        } else {
            Bitmap selectorBmp = cardSelector.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, selectorBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
        }

        Drag dragged = drag;
        if (dragged != null && dragged.getItem() != null) {
            Bitmap draggingItemBmp = dragged.getItem().toBitmap();
            Utils.drawBitmapOnCanvas(canvas, draggingItemBmp, paint, dragged.x() - draggingItemBmp.getWidth() / 2, dragged.y() - draggingItemBmp.getHeight() / 2);
        }

        Bitmap winBmp = window.toBitmap();
        int winPosY = Utils.screenHeight() - winBmp.getHeight();
        Utils.drawBitmapOnCanvas(canvas, winBmp, paint, Utils.DRAW_POSITION_CENTER, winPosY);
        return duelBmp;
    }

    public SelectableItem getCurrentSelectItem() {
        return currentSelectItem;
    }

    public Item getCurrentSelectItemContainer() {
        return currentSelectItemContainer;
    }

    public Dice getDice() {
        return dice;
    }

    public Coin getCoin() {
        return coin;
    }
}
