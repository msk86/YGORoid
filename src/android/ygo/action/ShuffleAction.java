package android.ygo.action;

import android.ygo.core.Deck;
import android.ygo.touch.Touch;

public class ShuffleAction extends BaseAction {

    public ShuffleAction(Touch touch) {
        super(touch.getDuel(), touch.getContainer(), touch.getItem());
    }

    @Override
    public void execute() {
        Deck deck = (Deck)item;
        deck.shuffle();
        // show animation of shuffle
    }
}
