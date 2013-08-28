package org.msk86.ygoroid.action;

import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class OpenMenuAction extends BaseAction {
    public OpenMenuAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        YGOActivity activity = Utils.getContext();
        activity.openOptionsMenu();
    }
}
