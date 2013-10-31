package org.msk86.ygoroid.views.deckbuilder.filter;

import org.msk86.ygoroid.core.Race;

public class RaceFilter implements CardFilter {

    Race race;

    public RaceFilter(Race race) {
        this.race = race;
    }

    @Override
    public String where() {
        if (!isValid()) {
            return "";
        }
        return " AND d.race & " + race.getCode() + " = " + race.getCode();
    }

    @Override
    public boolean isValid() {
        return race != Race.NULL;
    }
}
