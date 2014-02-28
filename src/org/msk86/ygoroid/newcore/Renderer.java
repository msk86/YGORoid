package org.msk86.ygoroid.newcore;

import android.graphics.Canvas;
import org.msk86.ygoroid.size.Size;

public interface Renderer {
    public Size size();
    public void draw(Canvas canvas, int x, int y);
}
