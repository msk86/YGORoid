package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.op.StartDrag;

public class DragFieldCardAction extends BaseAction {
    public DragFieldCardAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        Field field = (Field) container;
        field.removeItem();
        duel.select(card, container);
        ((StartDrag) operation).setDragItem(card);
    }
}
