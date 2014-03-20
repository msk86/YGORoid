package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.impl.SpCard;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class SpCardGenerator implements BmpGenerator {
    private static Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
    private SpCard card;

    public SpCardGenerator(SpCard card) {
        this.card = card;
    }

    private String cardBmpKey(Size size) {
        return card.getName() + size.toString();
    }

    @Override
    public Bitmap generate(Size size) {
        if (cache.get(cardBmpKey(size)) == null || cache.get(cardBmpKey(size)).isRecycled()) {
            cache.put(cardBmpKey(size), bmp(size));
        }

        return cache.get(cardBmpKey(size));
    }

    private Bitmap bmp(Size size) {
        return BmpReader.readBitmap(card.getResId(), size);
    }
}
