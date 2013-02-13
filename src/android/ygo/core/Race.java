package android.ygo.core;

public enum Race {
    NULL(Const.NULL, ""),
    WARRIOR(Const.RACE_WARRIOR, "战士"),
    SPELL_CASTER(Const.RACE_SPELLCASTER, "魔法师"),
    FAIRY(Const.RACE_FAIRY, "天使"),
    FIEND(Const.RACE_FIEND, "恶魔"),
    ZOMBIE(Const.RACE_ZOMBIE, "不死"),
    MACHINE(Const.RACE_MACHINE, "机械"),
    AQUA(Const.RACE_AQUA, "水"),
    PYRO(Const.RACE_PYRO, "炎"),
    ROCK(Const.RACE_ROCK, "岩石"),
    WIND_BEAST(Const.RACE_WINDBEAST, "鸟兽"),
    PLANT(Const.RACE_PLANT, "植物"),
    INSECT(Const.RACE_INSECT, "昆虫"),
    THUNDER(Const.RACE_THUNDER, "雷"),
    DRAGON(Const.RACE_DRAGON, "龙"),
    BEAST(Const.RACE_BEAST, "兽"),
    BEAST_WARRIOR(Const.RACE_BEASTWARRIOR, "兽战士"),
    DINOSAUR(Const.RACE_DINOSAUR, "恐龙"),
    FISH(Const.RACE_FISH, "鱼"),
    SEA_SERPENT(Const.RACE_SEASERPENT, "海龙"),
    REPTILE(Const.RACE_REPTILE, "爬虫"),
    PSYCHO(Const.RACE_PSYCHO, "念动力"),
    DEVINE(Const.RACE_DEVINE, "换神兽"),
    CREATOR_GOD(Const.RACE_CREATORGOD, "创世神");

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

    @Override
    public String toString() {
        return text;
    }
}
