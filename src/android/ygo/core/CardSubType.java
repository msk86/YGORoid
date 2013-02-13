package android.ygo.core;

import java.util.ArrayList;
import java.util.List;

public enum CardSubType {
    NORMAL(Const.TYPE_NORMAL, "通常"),
    EFFECT(Const.TYPE_EFFECT, "效果"),
    FUSION(Const.TYPE_FUSION, "融合"),
    RITUAL(Const.TYPE_RITUAL, "仪式"),
    TRAP_MONSTER(Const.TYPE_TRAPMONSTER, ""),
    SPIRIT(Const.TYPE_SPIRIT, "灵魂"),
    UNION(Const.TYPE_UNION, "联合"),
    DUAL(Const.TYPE_DUAL, "二重"),
    TUNER(Const.TYPE_TUNER, "调整"),
    SYNC(Const.TYPE_SYNCHRO, "同调"),
    TOKEN(Const.TYPE_TOKEN, "衍生物"),

    QUICK_PLAY(Const.TYPE_QUICKPLAY, "速攻"),
    CONTINUOUS(Const.TYPE_CONTINUOUS, "永续"),
    EQUIP(Const.TYPE_EQUIP, "装备"),
    FIELD(Const.TYPE_FIELD, "场地"),
    COUNTER(Const.TYPE_COUNTER, "反击"),

    FLIP(Const.TYPE_FLIP, "翻转"),
    TOON(Const.TYPE_TOON, "卡通"),
    XYZ(Const.TYPE_XYZ, "XYZ");


    private int code;
    private String text;

    CardSubType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static List<CardSubType> getCardSubType(int code) {
        List<CardSubType> types = new ArrayList<CardSubType>();
        for (CardSubType type : CardSubType.values()) {
            if ((code & type.code) == type.code) {
                types.add(type);
            }
        }
        return types;
    }

    @Override
    public String toString() {
        return text;
    }
}
