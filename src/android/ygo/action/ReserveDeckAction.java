package android.ygo.action;

import android.ygo.core.CardList;
import android.ygo.core.Deck;
import android.ygo.op.Operation;

public class ReserveDeckAction extends BaseAction {

    public ReserveDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Deck deck = (Deck) item;
        deck.reserve();
    }
}
