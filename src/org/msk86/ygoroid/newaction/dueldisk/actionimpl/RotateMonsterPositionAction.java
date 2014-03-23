package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Controllable;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newop.Operation;

public class RotateMonsterPositionAction extends BaseAction {
    public RotateMonsterPositionAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if (item instanceof Controllable) {
            ((Controllable) item).rotate();

            if(item instanceof Selectable) {
                duel.select((Selectable) item);
            }
        }
    }
}
