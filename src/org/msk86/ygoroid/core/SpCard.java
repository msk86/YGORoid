package org.msk86.ygoroid.core;

import android.graphics.Bitmap;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.utils.Utils;

public class SpCard extends Card {
    private int resId;

    public SpCard(String id, String name, String desc, int resId) {
        super(id, name, desc);
        this.resId = resId;

    }

    public static SpCard createMsk86() {
        return new SpCard("-1000", "About", "Developer: Msk86, Email: msk86.kaiser@gmail.com", R.raw.sp_msk86);
    }

    public static SpCard createZh99998() {
        return new SpCard("-1000", "About", "Partner: zh99998, Email: zh99998@gmail.com", R.raw.sp_zh99998);
    }

    public static SpCard createHeaven() {
        return new SpCard("-1000", "About", "Op: Heaven, Email: 20650688@qq.com", R.raw.sp_heaven);
    }

    @Override
    public Bitmap bmp(int width, int height) {
        return Utils.readBitmapScaleByHeight(resId, height);
    }
}
