package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.ygo.op.Drag;
import android.ygo.utils.Utils;

public class Duel implements Item {
    private DuelFields duelFields;

    private HandCards handCards;

    private CardSelector cardSelector;

    private InfoWindow window;

    private SelectableItem currentSelectItem;

    private Drag drag;

    public Duel() {
        duelFields = new DuelFields();
        Deck deck = new Deck("DECK");
        Deck exDeck = new Deck("EX");
        CardList graveyard = new CardList("GRAVEYARD");
        CardList removed = new CardList("REMOVED");
        TempList temp = new TempList("TEMPORARY");
        duelFields.getDeckField().setItem(deck);
        duelFields.getExDeckField().setItem(exDeck);
        duelFields.getGraveyardField().setItem(graveyard);
        duelFields.getRemovedField().setItem(removed);
        duelFields.getTempField().setItem(temp);

        handCards = new HandCards();

        window = new InfoWindow();
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
        }
        window.clearInfo();
    }

    public void select(SelectableItem item) {
        if (item != null) {
            if (item != currentSelectItem) {
                unSelect();
                currentSelectItem = item;
            }
            currentSelectItem.select();
        } else {
            unSelect();
        }

        if (item != null) {
            window.setInfo(item);
        }
    }

    public SelectableItem itemAt(int x, int y) {
        if (inDuelFields(x, y)) {
            return duelFields.itemOnFieldAt(x, y);
        } else if (inHand(x, y)) {
            return handCards.cardAt(x, y);
        } else if (inCardSelector(x, y)) {
            return cardSelector.cardAt(x, y);
        }
        return null;
    }

    public Item containerAt(int x, int y) {
        if (inDuelFields(x, y)) {
            return duelFields.fieldAt(x, y);
        } else if (inHand(x, y)) {
            return handCards;
        } else if (inCardSelector(x, y)) {
            return cardSelector.cardList;
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

    public boolean inCardSelector(int x, int y) {
        if(cardSelector == null) {
            return false;
        }
        return true;
    }

    public boolean inDuelFields(int x, int y) {
        if(cardSelector != null) {
            return false;
        }
        return y < Utils.unitLength() * 3 && x < Utils.unitLength() * 6;
    }

    public boolean inHand(int x, int y) {
        if(cardSelector != null) {
            return false;
        }
        return y >= Utils.unitLength() * 3 && x < Utils.unitLength() * 6;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap duelBmp = Bitmap.createBitmap(Utils.unitLength() * 6, Utils.unitLength() * 4, Bitmap.Config.ARGB_8888);

        Bitmap winBmp = window.toBitmap();
        Canvas canvas = new Canvas(duelBmp);
        Paint paint = new Paint();

        if(cardSelector == null) {
            Bitmap fieldBmp = duelFields.toBitmap();
            Bitmap handBmp = handCards.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, fieldBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            Utils.drawBitmapOnCanvas(canvas, handBmp, paint, Utils.DRAW_POSITION_FIRST, fieldBmp.getHeight() + 1);
        } else {
            Bitmap selectorBmp = cardSelector.toBitmap();
            Utils.drawBitmapOnCanvas(canvas, selectorBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_CENTER);
        }

        if (drag != null && drag.getItem() != null) {
            Bitmap draggingItemBmp = drag.getItem().toBitmap();
            Utils.drawBitmapOnCanvas(canvas, draggingItemBmp, paint, drag.x() - draggingItemBmp.getWidth() / 2, drag.y() - draggingItemBmp.getHeight() / 2);
        }

        int winPosY = Utils.screenHeight() - winBmp.getHeight();
        Utils.drawBitmapOnCanvas(canvas, winBmp, paint, Utils.DRAW_POSITION_CENTER, winPosY);
        return duelBmp;
    }

    public SelectableItem getCurrentSelectItem() {
        return currentSelectItem;
    }
}
