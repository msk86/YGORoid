package android.ygo.core;

public enum CardType {
    NULL(0, ""),
    MONSTER(Const.TYPE_MONSTER, "怪兽"),
    SPELL(Const.TYPE_SPELL, "魔法"),
    TRAP(Const.TYPE_TRAP, "陷阱");

    private int code;
    private String text;

    CardType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static CardType getCardType(int code) {
        for (CardType type : CardType.values()) {
            if ((code & type.code) == type.code) {
                return type;
            }
        }
        return NULL;
    }


    @Override
    public String toString() {
        return text;
    }
}
