package android.ygo.action;

import android.widget.Toast;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class ScreenShotAction extends BaseAction {
    public ScreenShotAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        String fileName = Utils.screenShot();
        if(fileName != null) {
            Toast.makeText(Utils.getContext(), "截图[" + fileName + "]成功。", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Utils.getContext(), "截图失败。", Toast.LENGTH_LONG).show();
        }
    }
}
