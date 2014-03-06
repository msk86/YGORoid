package org.msk86.ygoroid.newaction.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.actionimpl.IndicatorAction;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.impl.VolClick;

import java.util.ArrayList;
import java.util.List;

public class VolClickDispatcher implements Dispatcher<VolClick> {
    @Override
    public List<Action> dispatch(VolClick op) {
        List<Action> actionChain = new ArrayList<Action>();
        Item item = op.getItem();
        if(item != null) {
            if(op.getContainer() instanceof Field) {
                Field field = (Field) op.getContainer();
                if(field.getType() == FieldType.MONSTER || field.getType() == FieldType.MAGIC_TRAP
                        || field.getType() == FieldType.FIELD_MAGIC
                        || field.getType() == FieldType.PENDULUM_LEFT
                        || field.getType() == FieldType.PENDULUM_RIGHT) {
                    actionChain.add(new IndicatorAction(op));
                }
            }
        }
        return actionChain;
    }
}
