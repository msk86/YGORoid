package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.*;
import org.msk86.ygoroid.op.Drag;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.op.StartDrag;

public class RevertDragAction extends BaseAction {

    private Drag drag;

    public RevertDragAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
        drag = (Drag) operation;
    }

    @Override
    public void execute() {
        if (drag.getItem() == null) {
            return;
        }
        StartDrag startDrag = drag.getStartDrag();
        Item from = startDrag.getContainer();
        if (from instanceof Field) {
            Field field = (Field) from;
            SelectableItem fieldItem = field.getItem();
            if (fieldItem != null) {
                Card card = (Card) item;
                if (fieldItem instanceof CardList) {
                    ((CardList) fieldItem).push(card);
                } else if (fieldItem instanceof OverRay) {
                    ((OverRay) fieldItem).overRay(card);
                } else if (fieldItem instanceof Card) {
                    Card targetCard = (Card) fieldItem;
                    field.removeItem();
                    OverRay overRay = new OverRay(targetCard);
                    overRay.overRay(card);
                    field.setItem(overRay);
                }
            } else {
                ((Field) from).setItem(item);
            }
        } else if (from instanceof HandCards) {
            ((HandCards) from).add((Card) item);
        } else if (from instanceof CardList) {
            ((CardList) from).push((Card) item);
        }
        duel.unSelect();
    }
}
