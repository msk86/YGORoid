package org.msk86.ygoroid.views.logo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.newutils.Utils;
import org.msk86.ygoroid.size.OtherSize;

public class Logo {

    int resId;
    int stayTime = 1000;
    int intervalTime = 500;
    int maskColor = Color.BLACK;
    Bitmap logo = null;

    public Logo(int resId) {
        this.resId = resId;
        logo = BmpReader.readBitmap(resId, OtherSize.SCREEN);
    }

    public Logo(int resId, int maskColor) {
        this(resId);
        this.maskColor = maskColor;
    }
}
