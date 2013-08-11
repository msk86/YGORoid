package android.ygo.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.op.Operation;
import android.ygo.utils.Utils;

public class DeckBuilderAction extends BaseAction {
    public DeckBuilderAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        if (duel.getDeckName() != null) {
            AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                    .setTitle("放弃决斗开始组卡？")
                    .setPositiveButton("确定", new OnDeckBuilderClickListener("OK"))
                    .setNegativeButton("取消", new OnDeckBuilderClickListener("Cancel"))
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
