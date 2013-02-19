package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.op.StartDrag;

public class DragFieldCardAction extends BaseAction {
    public DragFieldCardAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        Field field = (Field) container;
        field.removeItem();
        duel.select(card);
        ((StartDrag) operation).setDragItem(card);
    }
}
