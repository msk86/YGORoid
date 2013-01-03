package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.FieldType;
import android.ygo.core.HandCards;
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
            if(field.getItem() == null) {
                action = new MoveAction(drag);
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
