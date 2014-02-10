package org.msk86.ygoroid.action;

import org.msk86.ygoroid.op.Operation;
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
