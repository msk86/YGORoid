package org.msk86.ygoroid.newcore.anime.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.anime.FlashMask;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class FlashMaskGenerator implements BmpGenerator {
    private static Map<String, Bitmap> cache = new HashMap<String, Bitmap>();

    FlashMask mask;

    public FlashMaskGenerator(FlashMask mask) {
        this.mask = mask;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(size.toString()) == null || cache.get(size.toString()).isRecycled()) {
            cache.put(size.toString(), bmp(size));
        }

        return cache.get(size.toString());
    }

    private Bitmap bmp(Size size) {
        return BmpReader.readBitmap(R.raw.mask, size);
    }
}
