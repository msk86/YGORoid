package android.ygo.action;

import android.ygo.core.*;
import android.ygo.touch.Click;
import android.ygo.touch.DoubleClick;
import android.ygo.touch.Drag;

public class ActionDispatcher {

    public static Action dispatch(Click click) {
        Action action = new EmptyAction();
        if(click.getItem() != null) {
            action = new SelectAction(click);
        }
        return action;
    }
    public static Action dispatch(DoubleClick dblClick) {
        Action action = new EmptyAction();
        if(dblClick.getItem() != null) {
            if(dblClick.getContainer() instanceof Field) {
                Field field = (Field)dblClick.getContainer();
                if(field.getType() == FieldType.MONSTER_ZONE) {
                    action = new MonsterPositionAction(dblClick);
                }
            }
        }
        return action;
    }
    public static Action dispatch(Drag drag) {
        Action action = new RevertDragAction(drag);
        if(drag.getContainer() instanceof Field) {
            Field field = (Field)drag.getContainer();
            SelectableItem targetItem = field.getItem();
            if(targetItem == null) {
                action = new MoveAction(drag);
            }
            if(drag.getItem() instanceof Card && (targetItem instanceof Card || targetItem instanceof Overlay)) {
                action = new OverlayAction(drag);
            }
            if((drag.getItem() instanceof Card || drag.getItem() instanceof Overlay) && targetItem instanceof CardList) {
                action = new AddCardListAction(drag);
            }
        }
        if(drag.getContainer() instanceof HandCards) {
            if(drag.getItem() instanceof Card) {
                action = new AddHandCardAction(drag);
            }
        }
        return action;
    }
}
