package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.core.OverRay;
import org.msk86.ygoroid.op.Operation;

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
