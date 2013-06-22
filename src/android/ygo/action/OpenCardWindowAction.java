package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.OverRay;
import android.ygo.core.ShowCardWindow;
import android.ygo.op.Operation;

public class OpenCardWindowAction extends BaseAction {
    public OpenCardWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card;
        if (item instanceof OverRay) {
            card = ((OverRay) item).topCard();
        } else {
            card = (Card) item;
        }

        ShowCardWindow cardWindow = new ShowCardWindow(card);
        duel.setCardWindow(cardWindow);
    }
}
