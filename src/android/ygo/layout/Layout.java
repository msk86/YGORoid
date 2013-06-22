package android.ygo.layout;

import android.ygo.core.Card;
import android.ygo.core.Item;

import java.util.List;

public interface Layout extends Item {

    public Card cardAt(int x, int y);

    public List<Card> cards();
}
