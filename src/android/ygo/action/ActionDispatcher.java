package android.ygo.action;

import android.ygo.core.Field;
import android.ygo.core.FieldType;
import android.ygo.touch.Click;
import android.ygo.touch.DoubleClick;
import android.ygo.touch.Touch;

public class ActionDispatcher {

    public static Action dispatch(Touch touch) {
        Action action = new EmptyAction();
        if(touch instanceof Click && touch.getItem() != null) {
            action = new SelectAction(touch);
        }
        if(touch instanceof DoubleClick && touch.getItem() != null) {
            if(touch.getContainer() instanceof Field) {
                Field field = (Field)touch.getContainer();
                if(field.getType() == FieldType.MONSTER_ZONE) {
                    action = new MonsterPositionAction(touch);
                }
            }
        }
        return action;
    }
}
