package org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.RotateMonsterPositionAction;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.impl.Press;

import java.util.ArrayList;
import java.util.List;

public class PressDispatcher implements Dispatcher<Press> {
    @Override
    public List<Action> dispatch(Press op) {
        List<Action> actionChain = new ArrayList<Action>();
        Container container = op.getContainer();
        if(container instanceof Field) {
            Field field = (Field) container;
            if(op.getItem() instanceof Controllable && field.getType() == FieldType.MONSTER) {
                actionChain.add(new RotateMonsterPositionAction(op));
            }

        }
        return actionChain;
    }
}
