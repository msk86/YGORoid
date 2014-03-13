package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class FeedbackAction extends BaseAction {
    public FeedbackAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Utils.getContext().showFeedback();
    }
}
