package android.ygo.action;

import android.ygo.core.*;
import android.ygo.touch.Click;
import android.ygo.touch.DoubleClick;
import android.ygo.touch.Drag;
import android.ygo.touch.Press;

public class ActionDispatcher {

    public static Action dispatch(Click click) {
        Action action = new SelectAction(click);
        Duel duel = click.getDuel();
        if(duel.getCardSelector() != null) {
            if(!duel.inCardSelector(click.x(), click.y())) {
                action = new CloseCardSelectorAction(click);
            }
        }
        return action;
    }

    public static Action dispatch(Press press) {
        Action action = new EmptyAction();
        SelectableItem item = press.getItem();
        if (item != null) {
            if(item instanceof CardList) {
                action = new OpenCardSelectorAction(press);
            } else if(item instanceof Overlay) {
                Overlay overlay = (Overlay)item;
                if(overlay.topCard().getType() == CardType.XYZ_MONSTER && overlay.materialCount() != 0) {
                    action = new OpenCardSelectorAction(press);
                }
            }
        }
        return action;
    }

    public static Action dispatch(DoubleClick dblClick) {
        Action action = new EmptyAction();
        if (dblClick.getContainer() instanceof Field) {
            SelectableItem item = dblClick.getItem();
            if (item instanceof Card || item instanceof Overlay) {
                action = new FlipAction(dblClick);
            } else if(item instanceof Deck && ((Deck)item).getName() == "DECK") {
                action = new ShuffleAction(dblClick);
            }
        }
        return action;
    }

    public static Action dispatch(Drag drag) {
        Action action = new RevertDragAction(drag);
        if (drag.getContainer() instanceof Field) {
            Field field = (Field) drag.getContainer();
            SelectableItem targetItem = field.getItem();
            if (targetItem == null) {
                if (drag.getFrom() instanceof HandCards) {
                    action = new SummonAction(drag);
                } else {
                    action = new MoveAction(drag);
                }
            }
            if (drag.getItem() instanceof Card && (targetItem instanceof Card || targetItem instanceof Overlay)) {
                action = new OverlayAction(drag);
            }
            if ((drag.getItem() instanceof Card || drag.getItem() instanceof Overlay) && targetItem instanceof CardList) {
                action = new AddCardListAction(drag);
            }
        }
        if (drag.getContainer() instanceof HandCards) {
            if (drag.getItem() instanceof Card) {
                action = new AddHandCardAction(drag);
            }
        }
        return action;
    }
}
