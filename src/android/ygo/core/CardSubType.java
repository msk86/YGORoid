package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.R;
import android.ygo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public enum CardSubType {
    NORMAL(Const.TYPE_NORMAL, "通常", R.raw.card_normal_monster),
    EFFECT(Const.TYPE_EFFECT, "效果", R.raw.card_effect_monster),
    FUSION(Const.TYPE_FUSION, "融合", R.raw.card_fusion_monster),
    RITUAL(Const.TYPE_RITUAL, "仪式", R.raw.card_ritual_monster),
    TRAP_MONSTER(Const.TYPE_TRAPMONSTER, "", 0),
    SPIRIT(Const.TYPE_SPIRIT, "灵魂", 0),
    UNION(Const.TYPE_UNION, "联合", 0),
    DUAL(Const.TYPE_DUAL, "二重", 0),
    TUNER(Const.TYPE_TUNER, "调整", 0),
    SYNC(Const.TYPE_SYNCHRO, "同调", R.raw.card_sync_monster),
    TOKEN(Const.TYPE_TOKEN, "衍生物", R.raw.card_token),

    QUICK_PLAY(Const.TYPE_QUICKPLAY, "速攻", 0),
    CONTINUOUS(Const.TYPE_CONTINUOUS, "永续", 0),
    EQUIP(Const.TYPE_EQUIP, "装备", 0),
    FIELD(Const.TYPE_FIELD, "场地", 0),
    COUNTER(Const.TYPE_COUNTER, "反击", 0),

    FLIP(Const.TYPE_FLIP, "翻转", 0),
    TOON(Const.TYPE_TOON, "卡通", 0),
    XYZ(Const.TYPE_XYZ, "XYZ", R.raw.card_xyz_monster);


    private int resId;
    private int code;
    private String text;
    private Bitmap cardBmp;

    CardSubType(int code, String text, int resId) {
        this.code = code;
        this.text = text;
        this.resId = resId;
        if (this.resId != 0) {
            initCardPic();
        }
    }

    private void initCardPic() {
        cardBmp = initCardPic(Utils.cardHeight());
    }

    public Bitmap initCardPic(int height) {
        return Utils.readBitmapScaleByHeight(resId, height);
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

    public Bitmap getCardBmp() {
        return cardBmp;
    }

    @Override
    public String toString() {
        return text;
    }
}
