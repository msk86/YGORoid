package org.msk86.ygoroid.views.deckbuilder.filter;

import org.msk86.ygoroid.core.Card;

public interface CardFilter {
    public String where();

    public boolean isValid();
}
