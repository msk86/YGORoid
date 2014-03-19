package org.msk86.ygoroid.newaction.deckbuilder.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class BuildDeckFinishAction extends BaseAction {
    public BuildDeckFinishAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.QUIT_DECK_BUILDER))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnExitClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnExitClickListener("Cancel"))
                .create();
        dialog.show();
    }

    private class OnExitClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnExitClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                Utils.getContext().showDuelWithDeck(deckBuilder.getCards().getDeckName());

            }
        }
    }
}
