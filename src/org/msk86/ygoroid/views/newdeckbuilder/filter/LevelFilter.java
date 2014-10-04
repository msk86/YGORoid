package org.msk86.ygoroid.views.newdeckbuilder.filter;

public class LevelFilter implements CardFilter {

    String level;

    public LevelFilter(String level) {
        this.level = level;
    }

    @Override
    public String where() {
        if (!isValid()) {
            return "";
        }
        return " AND d.level & 15 = " + level;
    }

    @Override
    public boolean isValid() {
        return level.length() != 0;
    }
}
