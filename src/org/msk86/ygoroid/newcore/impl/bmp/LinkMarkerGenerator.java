package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class LinkMarkerGenerator implements BmpGenerator {
    private static Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
    private int markerInt;
    private int[] markers;

    public LinkMarkerGenerator(int markerInt, int[] markers) {
        this.markerInt = markerInt;
        this.markers = markers;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(bmpKey(size)) == null || cache.get(bmpKey(size)).isRecycled()) {
            cache.put(bmpKey(size), bmp(size));
        }

        return cache.get(bmpKey(size));
    }

    private String bmpKey(Size size) {
        return "" + markerInt + size.toString();
    }

    private Bitmap bmp(Size size) {
        Bitmap linkMarker = BmpReader.readBitmap(R.raw.card_link_marker_off, size).copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(linkMarker);
        Paint paint = new Paint();

        for(int marker : markers) {
            Bitmap markerOn = BmpReader.readBitmap(marker, size);
            canvas.drawBitmap(markerOn, 0, 0, paint);
        }

        return linkMarker;
    }



    public static void clearCache() {
        for (String key : cache.keySet()) {
            cache.get(key).recycle();
        }
        cache.clear();
    }
}
