package android.ygo.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ygo.core.Card;
import android.ygo.op.Operation;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.io.File;
import java.util.List;

public class DeckChangeAction extends BaseAction {
    public DeckChangeAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle("请选择卡组");
        final String[] decks = listAllDecks();
        builder.setItems(decks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String deck = decks[which];
                List<List<Card>> cards = Utils.getDbHelper().loadFromFile(deck);
                List<Card> mainCards = cards.get(0);
                List<Card> exCards = cards.get(1);
                List<Card> sideCards = cards.get(2);
                duel.start(mainCards, exCards, sideCards);
                Utils.getContext().getDuelDiskView().updateActionTime();
            }
        });
        builder.create().show();
    }

    private String[] listAllDecks() {
        File deckPath = new File(Configuration.deckPath());
        return deckPath.list();
    }
}
