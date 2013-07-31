package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class UserDefinedCard extends Card {
    private String fileName;
    public UserDefinedCard(String name) {
        super("0", name, "");
        fileName = Configuration.userDefinedCardImgPath() + name + Configuration.cardImageSuffix();
    }

    @Override
    public Bitmap bmp(int width, int height) {
        return Utils.readBitmapScaleByHeight(fileName, height);
    }
}
