package android.ygo.core;

import android.graphics.Canvas;
import android.widget.Toast;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.op.Drag;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Duel implements Item, Drawable {
    private LifePoint lifePoint;

    private DuelFields duelFields;

    private HandCards handCards;

    private CardSelector cardSelector;

    private InfoWindow window;
    private ShowCardWindow cardWindow;
    private SideWindow sideWindow;

    private SelectableItem currentSelectItem;

    private Drag drag;
    private List<Card> mainDeckCards;
    private List<Card> exDeckCards;
    private List<Card> sideDeckCards;
    private Item currentSelectItemContainer;

    private Dice dice;
    private Coin coin;

    public Duel() {
       start(null ,null, null);
    }

    public void start(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if (mainDeckCards == null) {
            mainDeckCards = new ArrayList<Card>();
        }
        if (exDeckCards == null) {
            exDeckCards = new ArrayList<Card>();
        }
        if (sideDeckCards == null) {
            sideDeckCards = new ArrayList<Card>();
        }

        if(!deckCheck(mainDeckCards, exDeckCards, sideDeckCards)) {
            return;
        }

        initDuelField();

        this.mainDeckCards = mainDeckCards;
        this.exDeckCards = exDeckCards;
        this.sideDeckCards = sideDeckCards;

        initDeck();
        Deck deck = (Deck) duelFields.getDeckField().getItem();
        deck.shuffle();
        initHandCards();
    }

    public void restart() {
        start(this.mainDeckCards, this.exDeckCards, this.sideDeckCards);
    }

    public boolean deckCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if (!deckCardCountCheck(mainDeckCards, exDeckCards, sideDeckCards)) return false;
        if (!singleCardCountCheck(mainDeckCards, exDeckCards, sideDeckCards)) return false;
        return true;

    }

    private boolean deckCardCountCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if(mainDeckCards.size() == 0 && exDeckCards.size() == 0) {
            return true;
        }
        String info = null;
        if(mainDeckCards.size() < 40 || mainDeckCards.size() > 60) {
            info = "主卡组卡片数量不符合要求";
        } else if(exDeckCards.size() > 15) {
            info = "额外卡组卡片数量不符合要求";
        } else if(sideDeckCards.size() > 15) {
            info = "副卡组卡片数量不符合要求";
        }
        if(info != null) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
            window.setInfo(info);
            return false;
        }
        return true;
    }

    private boolean singleCardCountCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        List<Integer> invalidCardIds = new ArrayList<Integer>();
        List<String> invalidUDCardNames = new ArrayList<String>();

        List<Card> allCards = new ArrayList<Card>();
        allCards.addAll(mainDeckCards);
        allCards.addAll(exDeckCards);
        allCards.addAll(sideDeckCards);

        Map<String, Integer> cardCount = new TreeMap<String, Integer>();
        Map<String, Integer> uDCardCount = new TreeMap<String, Integer>();
        for (Card card : allCards) {
            if(!(card instanceof UserDefinedCard)) {
                if (!cardCount.containsKey(card.getRealId())) {
                    cardCount.put(card.getRealId(), 0);
                }
                cardCount.put(card.getRealId(), cardCount.get(card.getRealId()) + 1);
            } else {
                if (!uDCardCount.containsKey(card.getName())) {
                    uDCardCount.put(card.getName(), 0);
                }
                uDCardCount.put(card.getName(), uDCardCount.get(card.getName()) + 1);
            }
        }

        for(Map.Entry<String, Integer> entry : cardCount.entrySet()) {
            if(entry.getValue() > 3) {
                invalidCardIds.add(Integer.parseInt(entry.getKey()));
            }
        }

        for(Map.Entry<String, Integer> entry : uDCardCount.entrySet()) {
            if(entry.getValue() > 3) {
                invalidUDCardNames.add(entry.getKey());
            }
        }

        if(invalidCardIds.size() == 0 && invalidUDCardNames.size() == 0) {
            return true;
        }

        List<String> invalidCardNames = Utils.getDbHelper().loadNamesByIds(invalidCardIds);
        invalidCardNames.addAll(invalidUDCardNames);
        String info = "卡片" + invalidCardNames.toString() + "数量不符合要求";
        Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        window.setInfo(info);
        return false;
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

    public void setCardWindow(ShowCardWindow cardWindow) {
        this.cardWindow = cardWindow;
    }

    public void setSideWindow(SideWindow sideWindow) {
        this.sideWindow = sideWindow;
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
        } else if (inSideWindow(x, y)) {
            return sideWindow.cardAt(x, y);
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
            return currentSelectItemContainer;
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
        if (cardSelector != null || cardWindow != null || sideWindow != null) {
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
        if (cardSelector != null || cardWindow != null || sideWindow != null) {
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
        if (cardSelector != null || cardWindow != null || sideWindow != null) {
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
        if (cardSelector == null || cardWindow != null || sideWindow != null) {
            return false;
        }
        if (y >= Utils.screenHeight() - Utils.cardHeight() / 6) {
            return false;
        }
        return true;
    }

    public boolean inDuelFields(int x, int y) {
        if (cardSelector != null || cardWindow != null || sideWindow != null) {
            return false;
        }
        return y < Utils.unitLength() * 3 && x < Utils.totalWidth();
    }

    public boolean inHand(int x, int y) {
        if (cardSelector != null || cardWindow != null || sideWindow != null) {
            return false;
        }
        return y >= Utils.unitLength() * 3 && y < Utils.screenHeight() - Utils.cardHeight() / 6;
    }

    public boolean inInfo(int x, int y) {
        if(cardWindow != null) {
            return false;
        }
        return y >= Utils.screenHeight() - Utils.cardHeight() / 6;
    }

    public boolean inSideWindow(int x, int y) {
        if(sideWindow == null || cardWindow != null) {
            return false;
        }
        return y < Utils.screenHeight() - Utils.cardHeight() / 6;
    }

    @Override
    public int width() {
        return Utils.totalWidth();
    }

    @Override
    public int height() {
        return Utils.unitLength() * 4;
    }


    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);

        if (cardSelector == null && cardWindow == null) {
            helper.drawDrawable(canvas, lifePoint, Utils.unitLength(), (Utils.unitLength() - lifePoint.height()) / 2);
            helper.drawDrawable(canvas, dice, (int) (Utils.unitLength() * 2.4), 0);
            helper.drawDrawable(canvas, coin, (int) (Utils.unitLength() * 2.4), Utils.unitLength() / 2);
            helper.drawDrawable(canvas, duelFields, 0, 0);
            helper.drawDrawable(canvas, handCards, 0, duelFields.height() + 1);
        }
        if(cardSelector != null) {
            helper.drawDrawable(canvas, cardSelector, 0, 0);
        }
        if(sideWindow != null) {
            helper.drawDrawable(canvas, sideWindow, 0, 0);
        }

        Drag dragged = drag;
        if (dragged != null && dragged.getItem() != null) {
            if (dragged.getItem() instanceof Drawable) {
                Drawable drawable = (Drawable) dragged.getItem();
                helper.drawDrawable(canvas, drawable, dragged.x() - drawable.width() / 2, dragged.y() - drawable.height() / 2);
            }
        }

        helper.drawDrawable(canvas, window, helper.center(width(), window.width()), helper.bottom(Utils.screenHeight(), window.height()));

        if(cardWindow != null) {
            helper.drawDrawable(canvas, cardWindow, 0, 0);
        }
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

    public ShowCardWindow getCardWindow() {
        return cardWindow;
    }

    public SideWindow getSideWindow() {
        return sideWindow;
    }

    public List<Card> getMainDeckCards() {
        return mainDeckCards;
    }

    public List<Card> getExDeckCards() {
        return exDeckCards;
    }

    public List<Card> getSideDeckCards() {
        return sideDeckCards;
    }
}
