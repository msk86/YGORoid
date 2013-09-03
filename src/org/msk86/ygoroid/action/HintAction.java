package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class HintAction extends BaseAction {

    public HintAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Utils.getContext().showHint();
    }
}
