package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.op.Operation;

public class AddCardListAction extends BaseAction {
    public AddCardListAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        CardList cl = (CardList) ((Field) container).getItem();
        if (item instanceof Card) {
            cl.push((Card) item);
        } else if (item instanceof OverRay) {
            OverRay ol = (OverRay) item;
            cl.push(ol.getOverRayUnits());
            Card xyzMonster = ol.topCard();
            if (xyzMonster != null) {
                cl.push(xyzMonster);
            }
        }
        duel.select(cl, container);
    }
}
