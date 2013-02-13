package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Drag;
import android.ygo.op.Operation;
import android.ygo.op.StartDrag;

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
            SelectableItem fieldItem = ((Field) from).getItem();
            if (fieldItem != null) {
                if (fieldItem instanceof CardList) {
                    ((CardList) fieldItem).push((Card) item);
                } else if (fieldItem instanceof Overlay) {
                    ((Overlay) fieldItem).overlay((Card) item);
                }
            } else {
                ((Field) from).setItem(item);
            }
        } else if (from instanceof HandCards) {
            ((HandCards) from).add((Card) item);
        } else if (from instanceof CardList) {
            ((CardList) from).push((Card) item);

        }
    }
}
