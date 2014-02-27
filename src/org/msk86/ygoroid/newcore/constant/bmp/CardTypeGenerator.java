package org.msk86.ygoroid.newcore.constant.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.constant.CardType;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.BmpReader;
import org.msk86.ygoroid.utils.Utils2;

import java.util.HashMap;
import java.util.Map;

public class CardTypeGenerator implements BmpGenerator {
    private CardType type;
    private Map<Size, Bitmap> cache = new HashMap<Size, Bitmap>();

    public CardTypeGenerator(CardType type) {
        this.type = type;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(size) == null) {
            cache.put(size, BmpReader.readBitmap(type.getResId(), size));
        }
        return cache.get(size);
    }
}
