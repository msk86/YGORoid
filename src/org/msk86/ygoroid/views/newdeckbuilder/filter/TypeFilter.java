package org.msk86.ygoroid.views.newdeckbuilder.filter;

import org.msk86.ygoroid.core.CardSubType;
import org.msk86.ygoroid.core.CardType;

public class TypeFilter implements CardFilter {

    CardType type;
    CardSubType subType;

    public TypeFilter(CardType type, CardSubType subType) {
        this.type = type;
        this.subType = subType;
    }

    @Override
    public String where() {
        if (!isValid()) {
            return "";
        }
        String w = " AND d.type & " + type.getCode() + " = " + type.getCode();

        if (subType == CardSubType.NULL) {
            w += "";
        } else {
            if ((type == CardType.SPELL || type == CardType.TRAP) && (subType == CardSubType.NORMAL)) {
                w += " AND d.type = " + type.getCode();
            } else {
                w += " AND d.type & " + subType.getCode() + " = " + subType.getCode();
            }
        }
        return w;
    }

    @Override
    public boolean isValid() {
        return type != CardType.NULL;
    }
}
