package android.ygo.action;

import android.util.Log;
import android.ygo.core.*;
import android.ygo.op.*;

public class ActionDispatcher {

    public static Action dispatch(Click click) {
        Action action = new SelectAction(click);
        return action;
    }

    public static Action dispatch(Press press) {
        Action action = new SelectAction(press);
        if (press.getContainer() instanceof Field) {
            Field field = (Field)press.getContainer();
            if(field.getType() == FieldType.MONSTER_ZONE) {
                SelectableItem item = press.getItem();
                if (item instanceof Card || item instanceof Overlay) {
                    action = new MonsterPositionAction(press);
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
                    // summon
                    action = new SummonAction(drag);
                    // set
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
    public static Action dispatch(ReturnClick click) {
        Action action = new EmptyAction();
        if(click.getDuel().getCardSelector() != null) {
            action = new CloseCardSelectorAction(click);
        }
        return action;
    }
    public static Action dispatch(MenuClick click) {
        Action action = new EmptyAction();
        SelectableItem item = click.getItem();
        if (item != null) {
            if(item instanceof CardList) {
                action = new OpenCardSelectorAction(click);
            } else if(item instanceof Overlay) {
                Overlay overlay = (Overlay)item;
                if(overlay.topCard().getType() == CardType.XYZ_MONSTER && overlay.materialCount() != 0) {
                    action = new OpenCardSelectorAction(click);
                }
            }
        }
        return action;
    }
}
