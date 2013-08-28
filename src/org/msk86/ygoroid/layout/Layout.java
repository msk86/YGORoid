package org.msk86.ygoroid.layout;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Item;

import java.util.List;

public interface Layout extends Item {

    public Card cardAt(int x, int y);

    public List<Card> cards();
}
