package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
import android.ygo.touch.Touch;

public class AddCardListAction extends BaseAction {
    public AddCardListAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        CardList cl = (CardList) ((Field) container).getItem();
        if (item instanceof Card) {
            cl.push((Card) item);
        } else if (item instanceof Overlay) {
            Overlay ol = (Overlay) item;
            cl.push(ol.getMaterials());
            Card xyzMonster = ol.topCard();
            if (xyzMonster != null) {
                cl.push(xyzMonster);
            }
        }
    }
}
