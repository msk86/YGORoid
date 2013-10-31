package org.msk86.ygoroid.views.deckbuilder.filter;

import org.msk86.ygoroid.core.Attribute;
import org.msk86.ygoroid.core.Card;

public class AttrFilter implements CardFilter {

    Attribute attr;

    public AttrFilter(Attribute attr) {
        this.attr = attr;
    }

    @Override
    public String where() {
        if(!isValid()) {
            return "";
        }
        return " AND d.attribute & " + attr.getCode() + " = " + attr.getCode();
    }

    @Override
    public boolean isValid() {
        return attr != Attribute.NULL;
    }
}
