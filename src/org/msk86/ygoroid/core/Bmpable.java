package org.msk86.ygoroid.core;

import android.graphics.Bitmap;

public interface Bmpable {
    public Bitmap bmp(int width, int height);

    public void destroyBmp();
}
