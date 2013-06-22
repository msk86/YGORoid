package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.layout.Layout;
import android.ygo.op.Operation;

public class SelectSideAction extends BaseAction {
    public SelectSideAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        duel.select(item, container);
        duel.getSideWindow().setSelectCard((Card) item, (Layout) container);
    }
}
