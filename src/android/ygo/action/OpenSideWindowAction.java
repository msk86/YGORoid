package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.SideWindow;
import android.ygo.op.Operation;

import java.util.List;

public class OpenSideWindowAction extends BaseAction {
    public OpenSideWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        openAll(duel.getMainDeckCards());
        openAll(duel.getExDeckCards());
        openAll(duel.getSideDeckCards());
        SideWindow sideWindow = new SideWindow(duel.getMainDeckCards(), duel.getExDeckCards(), duel.getSideDeckCards());

        duel.setSideWindow(sideWindow);
        duel.unSelect();
    }

    private void openAll(List<Card> cards) {
        for(Card card : cards) {
            card.open();
            card.positive();
        }
    }
}
