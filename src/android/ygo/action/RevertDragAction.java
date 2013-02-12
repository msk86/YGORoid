package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Drag;
import android.ygo.op.Touch;

public class RevertDragAction extends BaseAction {

    private Drag drag;

    public RevertDragAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
        drag = (Drag) touch;
    }

    @Override
    public void execute() {
        if(drag.getItem() == null) {
            return;
        }
        Item from = drag.getFrom();
        if (from instanceof Field) {
            ((Field) from).setItem(item);
        } else if (from instanceof HandCards) {
            ((HandCards) from).add((Card) item);
        } else if (from instanceof CardList) {
            ((CardList) from).push((Card) item);
        } else if (from instanceof Overlay) {
            ((Overlay) from).overlay((Card) item);
        }
        duel.select(item);
    }
}
