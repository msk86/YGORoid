package android.ygo.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.core.Card;
import android.ygo.core.Duel;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

import java.util.List;

public class CloseSideWindowAction extends BaseAction {
    public CloseSideWindowAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle("副卡组更换完成？")
                .setPositiveButton("确定", new OnSideClickListener(duel, "OK"))
                .setNegativeButton("取消", new OnSideClickListener(duel, "Cancel"))
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
