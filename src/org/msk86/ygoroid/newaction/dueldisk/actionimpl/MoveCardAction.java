package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class MoveCardAction extends BaseAction {
    public MoveCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        if(item instanceof Controllable) {
            Controllable controllable = (Controllable) item;

            if(field.getType() == FieldType.MONSTER) {
                if(!controllable.isOpen()) {
                    controllable.negative();
                }
            } else {
                controllable.positive();
            }

            field.setItem((item));
        }
    }
}
