package android.ygo.core.tool;

import android.graphics.Bitmap;
import android.ygo.core.Bmpable;

import java.util.HashMap;
import java.util.Map;

public class BmpCache {
    Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
    private Bmpable bmpable;

    public BmpCache(Bmpable bmpable) {
        this.bmpable = bmpable;
    }

    public Bitmap get(int w, int h) {
        String wh = String.format("%d-%d", w, h);

        Bitmap bmp = cache.get(wh);
        if(bmp == null || bmp.isRecycled()) {
            bmp = bmpable.bmp(w, h);
            cache.put(wh, bmp);
        }
        return bmp;
    }

    public void clear() {
        for(String key : cache.keySet()) {
            cache.get(key).recycle();
        }
        cache = new HashMap<String, Bitmap>();
    }
}
