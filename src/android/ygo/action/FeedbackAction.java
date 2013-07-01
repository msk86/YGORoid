package android.ygo.action;

import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class FeedbackAction extends BaseAction {

    public FeedbackAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Utils.getContext().showFeedback();
    }
}
