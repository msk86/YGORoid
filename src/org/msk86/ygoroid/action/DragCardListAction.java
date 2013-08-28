package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.op.StartDrag;

public class DragCardListAction extends BaseAction {
    public DragCardListAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = ((CardList) item).pop();
        ((StartDrag) operation).setDragItem(card);
    }
}
