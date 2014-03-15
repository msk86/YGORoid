package org.msk86.ygoroid.newcore.anime.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.anime.FlashMask;
import org.msk86.ygoroid.size.Size;

import java.util.Date;

public class FlashMaskRenderer implements Renderer {
    FlashMask mask;

    public FlashMaskRenderer(FlashMask mask) {
        this.mask = mask;
    }

    @Override
    public Size size() {
        return mask.getTargetItem().getRenderer().size();
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        long now = new Date().getTime();
        long liveTime = now - mask.getRefreshTime();
        if(liveTime > mask.getLife()) {
            mask.die();
        }
        if(!mask.isAlive()) return;

        canvas.save();
        canvas.translate(x, y);

        canvas.clipRect(0, 0, size().width(), size().height());

        Bitmap bmp = mask.getBmpGenerator().generate(size());

        double rate = 2.0 * liveTime / mask.getLife() - 1;

        canvas.drawBitmap(bmp, (int)(size().width() * rate), 0, new Paint());

        canvas.restore();
    }
}
