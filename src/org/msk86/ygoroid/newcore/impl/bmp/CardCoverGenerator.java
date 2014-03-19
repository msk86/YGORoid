package org.msk86.ygoroid.newcore.impl.bmp;

import android.graphics.Bitmap;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newutils.BmpReader;
import org.msk86.ygoroid.newutils.Configuration;
import org.msk86.ygoroid.size.Size;

public class CardCoverGenerator implements BmpGenerator {
    private Bitmap cover;

    @Override
    public Bitmap generate(Size size) {
        if (cover == null) {
            cover = BmpReader.readBitmap(Configuration.texturePath() + "cover" + Configuration.cardImageSuffix(), R.raw.cover, size);
        }
        return cover;
    }
}
