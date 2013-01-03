package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.touch.Drag;
import android.ygo.utils.Utils;

public class Duel implements Item {
    private DuelFields duelFields;

    private HandCards handCards;

    private InfoWindow window;

    private SelectableItem currentSelectItem;


    private Drag drag;

    public Duel() {
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
        if(currentSelectItem != null) {
            currentSelectItem.unSelect();
            currentSelectItem = null;
        }
        window.clearInfo();
    }

    public void select(SelectableItem item) {
        if(item != null) {
            if(item != currentSelectItem) {
                unSelect();
                currentSelectItem = item;
            }
            currentSelectItem.select();
        } else {
            unSelect();
        }

        if(item != null) {
            window.setInfo(item);
        }
    }

    public SelectableItem itemAt(int x, int y) {
        if(inDuelFields(x, y)) {
            return duelFields.itemOnFieldAt(x, y);
        } else if(inHand(x, y)) {
            return handCards.cardAt(x, y);
        }
        return null;
    }

    public Item containerAt(int x, int y) {
        if(inDuelFields(x, y)) {
            return duelFields.fieldAt(x, y);
        } else if(inHand(x, y)) {
            return handCards;
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

    public boolean inDuelFields(int x, int y) {
        return y <= Utils.unitLength() * 3 && x <= Utils.unitLength() * 6;
    }

    public boolean inHand(int x, int y) {
        return y> Utils.unitLength() * 3 && x <= Utils.unitLength() * 6;
    }

    @Override
    public Bitmap toBitmap() {
        Bitmap duelBmp = Bitmap.createBitmap(Utils.unitLength() * 6, Utils.unitLength() * 4, Bitmap.Config.ARGB_8888);

        Bitmap fieldBmp = duelFields.toBitmap();
        Bitmap handBmp = handCards.toBitmap();
        Bitmap winBmp = window.toBitmap();

        Canvas canvas = new Canvas(duelBmp);
        Paint paint = new Paint();
        Utils.drawBitmapOnCanvas(canvas, fieldBmp, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);

        Utils.drawBitmapOnCanvas(canvas, handBmp, paint, Utils.DRAW_POSITION_FIRST, fieldBmp.getHeight() + 1);

        int winPosY = Utils.screenHeight() - winBmp.getHeight();
        Utils.drawBitmapOnCanvas(canvas, winBmp, paint, Utils.DRAW_POSITION_CENTER, winPosY);

        if(drag != null && drag.getItem() != null) {
            Bitmap draggingItemBmp = drag.getItem().toBitmap();
            Utils.drawBitmapOnCanvas(canvas, draggingItemBmp, paint, drag.x()-draggingItemBmp.getWidth()/2, drag.y() - draggingItemBmp.getHeight()/2);
        }

        return duelBmp;
    }
}
