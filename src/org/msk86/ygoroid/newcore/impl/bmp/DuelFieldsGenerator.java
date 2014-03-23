package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.impl.DuelFields;
import org.msk86.ygoroid.newcore.impl.UserDefinedCard;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.newutils.Configuration;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.TextUtils;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class DuelFieldsGenerator implements BmpGenerator {
    Bitmap duelFieldsBmp;
    private DuelFields duelFields;

    public DuelFieldsGenerator(DuelFields duelFields) {
        this.duelFields = duelFields;
    }

    @Override
    public Bitmap generate(Size size) {
        if (duelFieldsBmp == null) {
            duelFieldsBmp = BmpReader.readBitmap(Configuration.texturePath() + "dueldisk.png", size);
        }

        return duelFieldsBmp;
    }
}
