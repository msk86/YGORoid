package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class FlipCardAction extends BaseAction {
    public FlipCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Controllable) {
            Controllable controllable = (Controllable) item;
            controllable.flip();

            if(item instanceof Selectable) {
                duel.select((Selectable) item);
            }
        }
    }
}
