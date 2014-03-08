package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class OpenMenuAction extends BaseAction {
    public OpenMenuAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        YGOActivity activity = Utils.getContext();
        activity.openOptionsMenu();
    }
}
