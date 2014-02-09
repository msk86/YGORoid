package org.msk86.ygoroid.views.logo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import org.msk86.ygoroid.utils.Utils;

public class Logo {

    int resId;
    int stayTime = 1000;
    int intervalTime = 500;
    int maskColor = Color.BLACK;
    Bitmap logo = null;

    public Logo(int resId) {
        this.resId = resId;
        logo = BitmapFactory.decodeResource(Utils.getContext().getResources(), resId);
        if(logo.getHeight() > Utils.screenHeight()) {
            logo = Utils.scaleByHeight(logo, Utils.screenHeight(), Bitmap.Config.ARGB_4444);
        }
    }

    public Logo(int resId, int maskColor) {
        this(resId);
        this.maskColor = maskColor;
    }
}
