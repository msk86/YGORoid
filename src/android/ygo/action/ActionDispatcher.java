package android.ygo.action;

import android.ygo.touch.Click;
import android.ygo.touch.Touch;

public class ActionDispatcher {

    public static Action dispatch(Touch touch) {
        Action action = new EmptyAction();
        if(touch instanceof Click) {
            action = new SelectAction(touch.getDuel(), touch.getItem());
        }
        return action;
    }
}
