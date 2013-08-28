package org.msk86.ygoroid.core;

import android.graphics.Canvas;

public interface Drawable {

    public void draw(Canvas canvas, int x, int y);

    public int width();

    public int height();
}
