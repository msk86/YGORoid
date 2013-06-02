package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
import android.ygo.core.SelectableItem;
import android.ygo.op.Operation;

public class OverlayAction extends BaseAction {

    public OverlayAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        Field field = (Field) container;
        SelectableItem targetItem = field.getItem();
        if (targetItem instanceof Overlay) {
            ((Overlay) targetItem).overlay(card);
        } else if (targetItem instanceof Card) {
            Card targetCard = (Card) targetItem;
            field.removeItem();
            Overlay overlay = new Overlay(targetCard);
            overlay.overlay(card);
            field.setItem(overlay);
        }
        duel.select(field.getItem(), container);
    }
}
