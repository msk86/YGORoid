package org.msk86.ygoroid.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class DeckChangeAction extends BaseAction {
    public DeckChangeAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle(Utils.s(R.string.CHOOSE_DECK));
        final String[] decks = Utils.decks();
        builder.setItems(decks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String deck = decks[which];
                duel.start(deck);
                Utils.getContext().getDuelDiskView().updateActionTime();
            }
        });
        builder.create().show();
    }
}
