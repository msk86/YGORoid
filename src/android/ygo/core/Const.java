package android.ygo.core;

public class Const {

    public static final int NULL = 0x0;
    // Type
    public static final int TYPE_MONSTER = 0x1;
    public static final int TYPE_SPELL = 0x2;
    public static final int TYPE_TRAP = 0x4;

    // SubType
    public static final int TYPE_NORMAL = 0x10;
    public static final int TYPE_EFFECT = 0x20;
    public static final int TYPE_FUSION = 0x40;
    public static final int TYPE_RITUAL = 0x80;
    public static final int TYPE_TRAPMONSTER = 0x100;
    public static final int TYPE_SPIRIT = 0x200;
    public static final int TYPE_UNION = 0x400;
    public static final int TYPE_DUAL = 0x800;
    public static final int TYPE_TUNER = 0x1000;
    public static final int TYPE_SYNCHRO = 0x2000;
    public static final int TYPE_TOKEN = 0x4000;
    public static final int TYPE_QUICKPLAY = 0x10000;
    public static final int TYPE_CONTINUOUS = 0x20000;
    public static final int TYPE_EQUIP = 0x40000;
    public static final int TYPE_FIELD = 0x80000;
    public static final int TYPE_COUNTER = 0x100000;
    public static final int TYPE_FLIP = 0x200000;
    public static final int TYPE_TOON = 0x400000;
    public static final int TYPE_XYZ = 0x800000;

    //Attributes
    public static final int ATTRIBUTE_EARTH = 0x01;
    public static final int ATTRIBUTE_WATER = 0x02;
    public static final int ATTRIBUTE_FIRE = 0x04;
    public static final int ATTRIBUTE_WIND = 0x08;
    public static final int ATTRIBUTE_LIGHT = 0x10;
    public static final int ATTRIBUTE_DARK = 0x20;
    public static final int ATTRIBUTE_DEVINE = 0x40;

    //Races
    public static final int RACE_WARRIOR = 0x1;
    public static final int RACE_SPELLCASTER = 0x2;
    public static final int RACE_FAIRY = 0x4;
    public static final int RACE_FIEND = 0x8;
    public static final int RACE_ZOMBIE = 0x10;
    public static final int RACE_MACHINE = 0x20;
    public static final int RACE_AQUA = 0x40;
    public static final int RACE_PYRO = 0x80;
    public static final int RACE_ROCK = 0x100;
    public static final int RACE_WINDBEAST = 0x200;
    public static final int RACE_PLANT = 0x400;
    public static final int RACE_INSECT = 0x800;
    public static final int RACE_THUNDER = 0x1000;
    public static final int RACE_DRAGON = 0x2000;
    public static final int RACE_BEAST = 0x4000;
    public static final int RACE_BEASTWARRIOR = 0x8000;
    public static final int RACE_DINOSAUR = 0x10000;
    public static final int RACE_FISH = 0x20000;
    public static final int RACE_SEASERPENT = 0x40000;
    public static final int RACE_REPTILE = 0x80000;
    public static final int RACE_PSYCHO = 0x100000;
    public static final int RACE_DEVINE = 0x200000;
    public static final int RACE_CREATORGOD = 0x400000;

    // menu group
    public static final int MENU_GROUP_DECK = 0x01;
    public static final int MENU_GROUP_CARD = 0x02;

    // menu id
    public static final int MENU_DECK_SHUFFLE = 0x01;
    public static final int MENU_DECK_REVERSE = 0x02;
    public static final int MENU_DECK_RESTART = 0x1024;
    public static final int MENU_DECK_CHANGE_DECK = 0x2048;

    public static final int MENU_CARD_BACK_TO_BOTTOM_OF_DECK = 0x01;
}
