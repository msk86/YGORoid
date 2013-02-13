package android.ygo.core;

public enum Attribute {
    NULL(0, ""), EARTH(1, "地"), WATER(2, "水"), FIRE(4, "火"), WIND(8, "风"), LIGHT(16, "光"), DARK(32, "暗");

    Attribute(int code, String chn) {
        this.code = code;
        this.chn = chn;
    }
    private int code;
    private String chn;

    public Attribute getAttribute(int code) {
        for(Attribute attr : Attribute.values()) {
            if(attr.code == code) {
                return attr;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return chn;
    }
}
