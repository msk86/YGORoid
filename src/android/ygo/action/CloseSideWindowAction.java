package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.SideWindow;
import android.ygo.op.Operation;

import java.util.List;

public class CloseSideWindowAction extends BaseAction {
    public CloseSideWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        closeAll(duel.getMainDeckCards());
        closeAll(duel.getExDeckCards());
        closeAll(duel.getSideDeckCards());

        duel.setSideWindow(null);
    }

    private void closeAll(List<Card> cards) {
        for(Card card : cards) {
            card.set();
        }
    }
}
