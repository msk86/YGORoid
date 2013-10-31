package org.msk86.ygoroid.views.deckbuilder.filter;

public class DefFilter implements CardFilter {

    String min;
    String max;

    public DefFilter(String min, String max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String where() {
        String w = "";
        if (min.length() != 0) {
            w += " AND d.def >= " + min;
        }
        if (max.length() != 0) {
            w += " AND d.def <= " + max;
        }
        return w;
    }

    @Override
    public boolean isValid() {
        return min.length() != 0 || max.length() != 0;
    }
}
