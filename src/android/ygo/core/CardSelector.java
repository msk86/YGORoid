package android.ygo.core;

import android.graphics.Bitmap;

public class CardSelector implements Item {

    CardList cardList;

    public CardSelector(CardList cardList) {
        this.cardList = cardList;
    }

    @Override
    public Bitmap toBitmap() {
        return null;
    }

    public Bitmap background() {
        return null;
    }
}
