package android.ygo.action;

import android.ygo.core.Deck;
import android.ygo.op.Operation;

public class ShuffleAction extends BaseAction {

    public ShuffleAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Deck deck = (Deck)item;
        deck.shuffle();
        // show animation of shuffle
    }
}
