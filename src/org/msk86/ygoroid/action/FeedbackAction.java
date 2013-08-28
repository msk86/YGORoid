package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class FeedbackAction extends BaseAction {

    public FeedbackAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Utils.getContext().showFeedback();
    }
}
