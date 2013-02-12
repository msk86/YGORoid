package android.ygo.core;

public enum Attribute {
    NULL(0), EARTH(1), WATER(2), FIRE(4), WIND(8), LIGHT(16), DARK(32);

    Attribute(int code) {
        this.code = code;
    }
    private int code;

    public Attribute getAttribute(int code) {
        for(Attribute attr : Attribute.values()) {
            if(attr.code == code) {
                return attr;
            }
        }
        return NULL;
    }
}
