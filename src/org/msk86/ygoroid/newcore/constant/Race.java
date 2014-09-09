package org.msk86.ygoroid.newcore.constant;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newutils.Utils;

public enum Race {
    NULL(Const.NULL, ""),
    WARRIOR(Const.RACE_WARRIOR, Utils.s(R.string.RACE_WARRIOR)),
    SPELL_CASTER(Const.RACE_SPELLCASTER, Utils.s(R.string.RACE_SPELLCASTER)),
    FAIRY(Const.RACE_FAIRY, Utils.s(R.string.RACE_FAIRY)),
    FIEND(Const.RACE_FIEND, Utils.s(R.string.RACE_FIEND)),
    ZOMBIE(Const.RACE_ZOMBIE, Utils.s(R.string.RACE_ZOMBIE)),
    MACHINE(Const.RACE_MACHINE, Utils.s(R.string.RACE_MACHINE)),
    AQUA(Const.RACE_AQUA, Utils.s(R.string.RACE_AQUA)),
    PYRO(Const.RACE_PYRO, Utils.s(R.string.RACE_PYRO)),
    ROCK(Const.RACE_ROCK, Utils.s(R.string.RACE_ROCK)),
    WIND_BEAST(Const.RACE_WINDBEAST, Utils.s(R.string.RACE_WINDBEAST)),
    PLANT(Const.RACE_PLANT, Utils.s(R.string.RACE_PLANT)),
    INSECT(Const.RACE_INSECT, Utils.s(R.string.RACE_INSECT)),
    THUNDER(Const.RACE_THUNDER, Utils.s(R.string.RACE_THUNDER)),
    DRAGON(Const.RACE_DRAGON, Utils.s(R.string.RACE_DRAGON)),
    BEAST(Const.RACE_BEAST, Utils.s(R.string.RACE_BEAST)),
    BEAST_WARRIOR(Const.RACE_BEASTWARRIOR, Utils.s(R.string.RACE_BEASTWARRIOR)),
    DINOSAUR(Const.RACE_DINOSAUR, Utils.s(R.string.RACE_DINOSAUR)),
    FISH(Const.RACE_FISH, Utils.s(R.string.RACE_FISH)),
    SEA_SERPENT(Const.RACE_SEASERPENT, Utils.s(R.string.RACE_SEASERPENT)),
    REPTILE(Const.RACE_REPTILE, Utils.s(R.string.RACE_REPTILE)),
    WYRMS(Const.RACE_WYRMS, Utils.s(R.string.RACE_WYRMS)),
    PSYCHO(Const.RACE_PSYCHO, Utils.s(R.string.RACE_PSYCHO)),
    DEVINE(Const.RACE_DEVINE, Utils.s(R.string.RACE_DEVINE)),
    CREATOR_GOD(Const.RACE_CREATORGOD, Utils.s(R.string.RACE_CREATORGOD));

    Race(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private int code;
    private String text;

    public static Race getRace(int code) {
        for (Race race : Race.values()) {
            if (race.code == code) {
                return race;
            }
        }
        return NULL;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return text;
    }
}
