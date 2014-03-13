package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class ToDeckBuilderAction extends BaseAction {
    public ToDeckBuilderAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if (duel.getDeckCards() != null) {
            AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                    .setTitle(Utils.s(R.string.DECK_BUILDER))
                    .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnDeckBuilderClickListener("OK"))
                    .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnDeckBuilderClickListener("Cancel"))
                    .create();
            dialog.show();
        } else {
            Utils.getContext().showDeckBuilderWithDeck(null);
        }
    }

    private class OnDeckBuilderClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnDeckBuilderClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                String deck = duel.getDeckCards().getDeckName();
                Utils.getContext().showDeckBuilderWithDeck(deck);
            }
        }
    }
}
