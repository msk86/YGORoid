package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.SideWindow;
import android.ygo.layout.Layout;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class DeckBuilderAction extends BaseAction {
    public DeckBuilderAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        String deck = duel.getDeckName();
        Utils.getContext().showDeckBuilderWithDeck(deck);
    }
}
