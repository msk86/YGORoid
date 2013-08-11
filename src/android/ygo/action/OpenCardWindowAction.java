package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Operation;

public class OpenCardWindowAction extends BaseAction {
    public OpenCardWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card;
        InfoWindow window = (InfoWindow) item;
        SelectableItem infoItem = window.getInfoItem();
        if (infoItem instanceof OverRay) {
            card = ((OverRay) infoItem).topCard();
        } else {
            card = (Card) infoItem;
        }

        if(!(card.isToken() && card.getId().equals("0"))) {
            ShowCardWindow cardWindow = new ShowCardWindow(card);
            duel.setCardWindow(cardWindow);
        }
    }
}
