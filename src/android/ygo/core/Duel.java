package android.ygo.core;

import android.graphics.Canvas;
import android.widget.Toast;
import android.ygo.core.tool.Coin;
import android.ygo.core.tool.Dice;
import android.ygo.op.Drag;
import android.ygo.utils.Utils;

import java.util.*;

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
    private List<Card> mainDeckCards = new ArrayList<Card>();
    private List<Card> exDeckCards = new ArrayList<Card>();
    private List<Card> sideDeckCards = new ArrayList<Card>();
    private Item currentSelectItemContainer;

    private Dice dice;
    private Coin coin;

    private String deckName;

    public Duel() {
        start(null, null, null);
    }

    public void start(String deck) {
        List<List<Card>> lists = Utils.getDbHelper().loadFromFile(deck);
        start(lists.get(0), lists.get(1), lists.get(2));
        deckName = deck;
    }

    private void start(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if (mainDeckCards == null) {
            mainDeckCards = new ArrayList<Card>();
        }
        if (exDeckCards == null) {
            exDeckCards = new ArrayList<Card>();
        }
        if (sideDeckCards == null) {
            sideDeckCards = new ArrayList<Card>();
        }

        if (!deckCheck(mainDeckCards, exDeckCards, sideDeckCards)) {
            return;
        }

        clearPreviousDuel();

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

    public void clearPreviousDuel() {
        List<Card> allCard = new ArrayList<Card>();
        allCard.addAll(mainDeckCards);
        allCard.addAll(exDeckCards);
        allCard.addAll(sideDeckCards);
        for (Card card : allCard) {
            card.destroyBmp();
        }
    }

    public boolean deckCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if (!deckCardCountCheck(mainDeckCards, exDeckCards, sideDeckCards)) return false;
        if (!singleCardCountCheck(mainDeckCards, exDeckCards, sideDeckCards)) return false;
        return true;

    }

    private boolean deckCardCountCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        if (mainDeckCards.size() == 0 && exDeckCards.size() == 0) {
            return true;
        }
        String info = null;
        if (!DeckChecker.checkMain(mainDeckCards)) {
            info = DeckChecker.ERROR_MAIN;
        } else if (!DeckChecker.checkEx(exDeckCards)) {
            info = DeckChecker.ERROR_EX;
        } else if (!DeckChecker.checkSide(sideDeckCards)) {
            info = DeckChecker.ERROR_SIDE;
        }
        if (info != null) {
            Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
            window.setInfo(info);
            return false;
        }
        return true;
    }

    private boolean singleCardCountCheck(List<Card> mainDeckCards, List<Card> exDeckCards, List<Card> sideDeckCards) {
        Set<Integer> invalidCardIds = new TreeSet<Integer>();
        Set<String> invalidUDCardNames = new TreeSet<String>();

        List<Card> allCards = new ArrayList<Card>();
        allCards.addAll(mainDeckCards);
        allCards.addAll(exDeckCards);
        allCards.addAll(sideDeckCards);

        for (Card card : allCards) {
            if(!DeckChecker.checkSingleCard(allCards, card)) {
                if(card instanceof UserDefinedCard || card.getRealId().equals("0")) {
                    invalidUDCardNames.add(card.getName());
                } else {
                    invalidCardIds.add(Integer.parseInt(card.getRealId()));
                }
            }
        }

        if (invalidCardIds.size() == 0 && invalidUDCardNames.size() == 0) {
            return true;
        }

        List<String> invalidCardNames = Utils.getDbHelper().loadNamesByIds(invalidCardIds);
        invalidCardNames.addAll(invalidUDCardNames);
        String info = String.format(DeckChecker.ERROR_CARD, invalidCardNames.toString());
        Toast.makeText(Utils.getContext(), info, Toast.LENGTH_LONG).show();
        window.setInfo(info);
        return false;
    }

    public void initDuelField() {
        lifePoint = new LifePoint();

        duelFields = new DuelFields();
        Deck deck = new Deck(CardList.DECK);
        Deck exDeck = new Deck(CardList.EX);
        CardList graveyard = new CardList(CardList.GRAVEYARD);
        CardList removed = new CardList(CardList.REMOVED);
        CardList temp = new CardList(CardList.TEMPORARY);
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
            return window;
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
        } else if (inSideWindow(x, y)) {
            return sideWindow.layoutAt(x, y);
        } else if (inInfo(x, y)) {
            return currentSelectItemContainer;
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
        if (y >= Utils.screenHeight() - window.height()) {
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
        return y >= Utils.unitLength() * 3 && y < Utils.screenHeight() - window.height();
    }

    public boolean inInfo(int x, int y) {
        if (cardWindow != null) {
            return false;
        }
        return y >= Utils.screenHeight() - window.height();
    }

    public boolean inSideWindow(int x, int y) {
        if (sideWindow == null || cardWindow != null) {
            return false;
        }
        return y < Utils.screenHeight() - window.height();
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

        helper.drawDrawable(canvas, lifePoint, Utils.unitLength(), (Utils.unitLength() - lifePoint.height()) / 2);
        helper.drawDrawable(canvas, dice, (int) (Utils.unitLength() * 2.4), 0);
        helper.drawDrawable(canvas, coin, (int) (Utils.unitLength() * 2.4), Utils.unitLength() / 2);
        helper.drawDrawable(canvas, duelFields, 0, 0);
        helper.drawDrawable(canvas, handCards, 0, duelFields.height() + 1);

        if (cardSelector != null) {
            helper.drawDrawable(canvas, cardSelector, 0, 0);
        }
        if (sideWindow != null) {
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

        if (cardWindow != null) {
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

    public boolean isSideWindow() {
        return sideWindow != null && cardWindow == null;
    }

    public boolean isCardSelector() {
        return cardSelector != null && cardWindow == null;
    }

    public boolean isDuelDisk() {
        return cardSelector == null && cardWindow == null && sideWindow == null;
    }

    public boolean isCardInfo() {
        return cardWindow != null;
    }

    public String getDeckName() {
        return deckName;
    }
}
