package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.core.OverRay;
import org.msk86.ygoroid.core.SelectableItem;
import org.msk86.ygoroid.op.StartDrag;

public class DragOverRayAction extends BaseAction {
    public DragOverRayAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        OverRay overRay = (OverRay) item;
        Field field = (Field) container;
        SelectableItem selectableItem;
        if (overRay.topCard().isSelect()) {
            Card overRayTopCard = overRay.removeTopCard();
            selectableItem = overRayTopCard;
            ((StartDrag) operation).setDragItem(overRayTopCard);
            overRay.adjust(field);
        } else {
            selectableItem = overRay;
            field.removeItem();
            ((StartDrag) operation).setDragItem(overRay);
        }
        duel.select(selectableItem, container);
    }
}
