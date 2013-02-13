package android.ygo.core;

public enum CardType {
    NULL(0, ""),
    NORMAL_MONSTER(0, "通常"),
    EFFECT_MONSTER(0, "效果"),
    RITUAL_MONSTER(0, "仪式"),
    FUSION_MONSTER(0, "融合"),
    SYNC_MONSTER(0, "同调"),
    XYZ_MONSTER(0, "XYZ"),

    NORMAL_MAGIC(0, "通常"),
    SPEED_MAGIC(0, "速攻"),
    EQUIP_MAGIC(0, "装备"),
    FOREVER_MAGIC(0, "永续"),
    FIELD_MAGIC(0, "场地"),

    NORMAL_TRAP(0, "通常"),
    FOREVER_TRAP(0, "永续"),
    COUNTER_TRAP(0, "反击"),

    TOKEN(0, "衍生物")
    ;

    CardType(int code, String chn) {
        this.code = code;
        this.chn = chn;
    }
    private int code;
    private String chn;

    public CardType getCardType(int code) {
        for(CardType attr : CardType.values()) {
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
