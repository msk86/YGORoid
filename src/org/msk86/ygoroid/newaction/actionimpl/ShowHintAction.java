package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class ShowHintAction extends BaseAction {
    public ShowHintAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Utils.getContext().showHint();
    }
}
