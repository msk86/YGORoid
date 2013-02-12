package android.ygo.action;

import android.ygo.core.Field;
import android.ygo.op.Touch;

public class MoveAction extends BaseAction {

    public MoveAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        ((Field) container).setItem(item);
        duel.select(item);
    }
}
