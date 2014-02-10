package org.msk86.ygoroid.core;

import android.graphics.Bitmap;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.tool.BmpCache;
import org.msk86.ygoroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public enum CardSubType implements Bmpable {
    NULL(Const.NULL, "", 0),
    NORMAL(Const.TYPE_NORMAL, Utils.s(R.string.TYPE_NORMAL), R.raw.card_normal_monster),
    EFFECT(Const.TYPE_EFFECT, Utils.s(R.string.TYPE_EFFECT), R.raw.card_effect_monster),
    FUSION(Const.TYPE_FUSION, Utils.s(R.string.TYPE_FUSION), R.raw.card_fusion_monster),
    RITUAL(Const.TYPE_RITUAL, Utils.s(R.string.TYPE_RITUAL), R.raw.card_ritual_monster),
    TRAP_MONSTER(Const.TYPE_TRAPMONSTER, "", 0),
    SPIRIT(Const.TYPE_SPIRIT, Utils.s(R.string.TYPE_SPIRIT), 0),
    UNION(Const.TYPE_UNION, Utils.s(R.string.TYPE_UNION), 0),
    DUAL(Const.TYPE_DUAL, Utils.s(R.string.TYPE_DUAL), 0),
    TUNER(Const.TYPE_TUNER, Utils.s(R.string.TYPE_TUNER), 0),
    SYNC(Const.TYPE_SYNCHRO, Utils.s(R.string.TYPE_SYNCHRO), R.raw.card_sync_monster),
    TOKEN(Const.TYPE_TOKEN, Utils.s(R.string.TYPE_TOKEN), R.raw.card_token),

    QUICK_PLAY(Const.TYPE_QUICKPLAY, Utils.s(R.string.TYPE_QUICKPLAY), 0),
    CONTINUOUS(Const.TYPE_CONTINUOUS, Utils.s(R.string.TYPE_CONTINUOUS), 0),
    EQUIP(Const.TYPE_EQUIP, Utils.s(R.string.TYPE_EQUIP), 0),
    FIELD(Const.TYPE_FIELD, Utils.s(R.string.TYPE_FIELD), 0),
    COUNTER(Const.TYPE_COUNTER, Utils.s(R.string.TYPE_COUNTER), 0),

    FLIP(Const.TYPE_FLIP, Utils.s(R.string.TYPE_FLIP), 0),
    TOON(Const.TYPE_TOON, Utils.s(R.string.TYPE_TOON), 0),
    XYZ(Const.TYPE_XYZ, Utils.s(R.string.TYPE_XYZ), R.raw.card_xyz_monster);


    private int resId;
    private int code;
    private String text;
    BmpCache bmpCache;

    CardSubType(int code, String text, int resId) {
        this.code = code;
        this.text = text;
        this.resId = resId;
        bmpCache = new BmpCache(this);
    }

    @Override
    public Bitmap bmp(int width, int height) {
        return Utils.readBitmapScaleByHeight(resId, height);
    }

    @Override
    public void destroyBmp() {
        bmpCache.clear();
    }

    public static List<CardSubType> getCardSubType(int code) {
        List<CardSubType> types = new ArrayList<CardSubType>();
        for (CardSubType type : CardSubType.values()) {
            if (type == NULL) {
                continue;
            }
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

    public int getCode() {
        return code;
    }
}
