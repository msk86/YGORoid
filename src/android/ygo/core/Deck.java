package android.ygo.core;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Deck implements SelectableItem {
    List<Card> cards;
    boolean reserved = false;

    public Deck(List<String> cardIds) {
        cards = new ArrayList<Card>();
        for(String id : cardIds) {
            cards.add(new Card(id));
        }
    }

    public void shuffle() {

    }

    public void reserve() {

    }

    public Card pop() {
        return null;
    }

    public void push(Card card) {

    }

    public void unShift(Card card) {

    }



    @Override
    public Bitmap highLight() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bitmap toBitmap() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
