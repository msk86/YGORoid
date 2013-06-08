package android.ygo.core;

import android.graphics.Canvas;

public interface Drawable {

    public void draw(Canvas canvas, int x, int y);

    public int width();

    public int height();
}
