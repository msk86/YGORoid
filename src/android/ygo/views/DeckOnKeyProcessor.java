package android.ygo.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.action.EmptyAction;
import android.ygo.op.ReturnClick;
import android.ygo.op.VolClick;
import android.ygo.utils.Utils;
import android.ygo.views.deckbuilder.DeckBuilderView;
import android.ygo.views.dueldisk.DuelDiskView;

public class DeckOnKeyProcessor {
    DeckBuilderView view;

    public DeckOnKeyProcessor(DeckBuilderView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        if (view.getCardWindow() == null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                        .setTitle("确定退出组卡，开始决斗吗？")
                        .setPositiveButton("确定", new OnExitClickListener("OK"))
                        .setNegativeButton("取消", new OnExitClickListener("Cancel"))
                        .create();
                dialog.show();
            }
        } else {
            view.setCardWindow(null);
            view.updateActionTime();
        }
        return true;
    }

    private class OnExitClickListener implements DialogInterface.OnClickListener {
        private String button;

        public OnExitClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ("OK".equals(button)) {
                Utils.getContext().showDuelWithDeck(view.getCurrentDeckName());
            }
        }
    }
}
