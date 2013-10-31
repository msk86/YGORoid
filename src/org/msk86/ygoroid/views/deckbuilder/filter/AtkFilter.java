package org.msk86.ygoroid.views.deckbuilder.filter;

public class AtkFilter implements CardFilter {

    String min;
    String max;

    public AtkFilter(String min, String max) {
        this.min = (min.contains("?") || min.contains("？")) ? "-1000" : min;
        this.max = (max.contains("?") || max.contains("？")) ? "-1" : max;
    }

    @Override
    public String where() {
        String w = "";
        if (min.length() != 0) {
            w += " AND d.atk >= " + min;
        }
        if (max.length() != 0) {
            w += " AND d.atk <= " + max;
        }
        return w;
    }

    @Override
    public boolean isValid() {
        return min.length() != 0 || max.length() != 0;
    }
}
