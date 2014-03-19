package org.msk86.ygoroid.newcore.constant.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.constant.CardSubType;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class CardSubTypeGenerator implements BmpGenerator {
    private CardSubType subType;
    private Map<Size, Bitmap> cache = new HashMap<Size, Bitmap>();

    public CardSubTypeGenerator(CardSubType subType) {
        this.subType = subType;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(size) == null) {
            cache.put(size, BmpReader.readBitmap(subType.getResId(), size));
        }
        return cache.get(size);
    }
}
