package org.msk86.ygoroid.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class DeckBuilderAction extends BaseAction {
    public DeckBuilderAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        if (duel.getDeckName() != null) {
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
                String deck = duel.getDeckName();
                Utils.getContext().showDeckBuilderWithDeck(deck);
            }
        }
    }
}
