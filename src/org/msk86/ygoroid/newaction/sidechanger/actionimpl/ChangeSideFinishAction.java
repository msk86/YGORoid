package org.msk86.ygoroid.newaction.sidechanger.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class ChangeSideFinishAction extends BaseAction {
    public ChangeSideFinishAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.CHANGE_SIDE))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnSideClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnSideClickListener("Cancel"))
                .create();
        dialog.show();

    }

    private class OnSideClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnSideClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                Utils.getContext().showDuelWithDeck(sideChanger.getCards());
            }
        }
    }
}
