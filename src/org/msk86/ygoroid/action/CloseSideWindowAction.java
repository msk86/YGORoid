package org.msk86.ygoroid.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.R;
import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Duel;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

import java.util.List;

public class CloseSideWindowAction extends BaseAction {
    public CloseSideWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(Utils.s(R.string.CHANGE_SIDE))
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnSideClickListener(duel, "OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnSideClickListener(duel, "Cancel"))
                .create();
        dialog.show();

    }

    private void closeAll(List<Card> cards) {
        for(Card card : cards) {
            card.set();
        }
    }

    private class OnSideClickListener implements DialogInterface.OnClickListener {

        private Duel duel;
        private String button;

        public OnSideClickListener(Duel duel, String button) {
            this.duel = duel;
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                closeAll(duel.getMainDeckCards());
                closeAll(duel.getExDeckCards());
                closeAll(duel.getSideDeckCards());

                duel.setSideWindow(null);
                duel.restart();
            }
        }
    }
}
