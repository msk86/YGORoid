package android.ygo.core;

public enum Attribute {
    NULL(Const.NULL, ""),
    EARTH(Const.ATTRIBUTE_EARTH, "地"),
    WATER(Const.ATTRIBUTE_WATER, "水"),
    FIRE(Const.ATTRIBUTE_FIRE, "火"),
    WIND(Const.ATTRIBUTE_WIND, "风"),
    LIGHT(Const.ATTRIBUTE_LIGHT, "光"),
    DARK(Const.ATTRIBUTE_DARK, "暗"),
    DIVINE(Const.ATTRIBUTE_DEVINE, "神");

    Attribute(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private int code;
    private String text;

    public static Attribute getAttribute(int code) {
        for (Attribute attr : Attribute.values()) {
            if (attr.code == code) {
                return attr;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return text;
    }
}
