package org.msk86.ygoroid.views.deckbuilder.filter;

import org.msk86.ygoroid.core.Card;

public class AtkFilter implements CardFilter {

    String min;
    String max;

    public AtkFilter(String min, String max) {
        this.min = (min.equals("?") || min.equals("？")) ? "-1" : min;
        this.max = (max.equals("?") || max.equals("？")) ? "-1" : max;
    }

    @Override
    public String where() {
        String w = "";
        if(min.length() != 0) {
            w += " AND d.atk >= " + min;
        }
        if(max.length() != 0) {
            w += " AND d.atk <= " + max;
        }
        return w;
    }

    @Override
    public boolean isValid() {
        return min.length() != 0 || max.length() != 0;
    }
}
