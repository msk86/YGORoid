package org.msk86.ygoroid.newcore.constant.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.constant.CardType;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.size.Size;

import java.util.HashMap;
import java.util.Map;

public class FieldTypeGenerator implements BmpGenerator {
    private FieldType type;
    private Map<Size, Bitmap> cache = new HashMap<Size, Bitmap>();

    public FieldTypeGenerator(FieldType type) {
        this.type = type;
    }

    @Override
    public Bitmap generate(Size size) {
        if(cache.get(size) == null) {
            cache.put(size, BmpReader.readBitmap(type.getDefaultBgResId(), size));
        }
        return cache.get(size);
    }

    @Override
    public void destroy() {
    }
}
