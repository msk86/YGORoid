package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.op.StartDrag;

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
