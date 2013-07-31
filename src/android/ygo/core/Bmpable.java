package android.ygo.core;

import android.graphics.Bitmap;

public interface Bmpable {
    public Bitmap bmp(int width, int height);

    public void destroyBmp();
}
