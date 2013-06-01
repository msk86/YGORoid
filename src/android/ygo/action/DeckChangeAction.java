package android.ygo.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.ygo.YGOActivity;
import android.ygo.core.Card;
import android.ygo.core.CardList;
import android.ygo.core.Field;
import android.ygo.core.Overlay;
import android.ygo.op.Operation;
import android.ygo.sqlite.CardsDBHelper;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import android.ygo.views.DuelDiskView;

import java.io.File;
import java.util.List;

public class DeckChangeAction extends BaseAction {
    public DeckChangeAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        builder.setTitle("Choose Deck");
        final String[] decks = listAllDecks();
        builder.setItems(decks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String deck = decks[which];
                List<List<Card>> cards = Utils.getDbHelper().loadFromFile(deck);
                List<Card> mainCards = cards.get(0);
                List<Card> exCards = cards.get(1);
                duel.initDuelField();
                duel.start(mainCards, exCards);
            }
        });
        builder.create().show();
    }

    private String[] listAllDecks() {
        File deckPath = new File(Configuration.deckPath());
        return deckPath.list();
    }
}
