package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.utils.BmpReader;
import org.msk86.ygoroid.utils.Configuration;

public class CardCoverGenerator implements BmpGenerator {
    private Bitmap cover;

    @Override
    public Bitmap generate(Size size) {
        if (cover == null) {
            Bitmap bmp = BmpReader.readBitmap(Configuration.texturePath() + "cover" + Configuration.cardImageSuffix(), size);
            if (bmp == null) {
                bmp = BmpReader.readBitmap(R.raw.cover, size);
            }
            cover = bmp;
        }
        return cover;
    }
}
