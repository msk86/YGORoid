package android.ygo.action;

import android.ygo.op.Operation;
import android.ygo.utils.Configuration;

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
