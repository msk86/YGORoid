package org.msk86.ygoroid.newaction.actionimpl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class ChangeDeckAction extends BaseAction {
    public ChangeDeckAction(Operation operation) {
        super(operation);
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
