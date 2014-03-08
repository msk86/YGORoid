package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.utils.Configuration;

public class ToggleAction extends BaseAction {
    private String toggle;
    public ToggleAction(Operation operation, String toggle) {
        super(operation);
        this.toggle = toggle;
    }

    @Override
    public void execute() {
        Configuration.configProperties(toggle, !Configuration.configProperties(toggle));
        Configuration.saveProperties();
    }
}
