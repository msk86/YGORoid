package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
import android.ygo.core.SelectableItem;
import android.ygo.op.StartDrag;

public class DragOverlayAction extends BaseAction {
    public DragOverlayAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        Overlay overlay = (Overlay) item;
        Field field = (Field) container;
        SelectableItem selectableItem;
        if (overlay.topCard().isSelect() && overlay.totalCard() != 1) {
            Card overlayTopCard = overlay.removeTopCard();
            selectableItem = overlayTopCard;
            ((StartDrag) operation).setDragItem(overlayTopCard);
            if (overlay.totalCard() == 1) {
                Card lastCard = overlay.removeTopCard();
                field.removeItem();
                field.setItem(lastCard);
            }
        } else {
            selectableItem = overlay;
            ((StartDrag) operation).setDragItem(overlay);
            field.removeItem();
        }
        duel.select(selectableItem);
    }
}
