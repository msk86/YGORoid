package android.ygo.core;

import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class UserDefinedCard extends Card {
    public UserDefinedCard(String name) {
        super("0", name, "");
        String fileName = Configuration.userDefinedCardImgPath() + name + Configuration.cardImageSuffix();
        super.cardPic = Utils.readBitmapScaleByHeight(fileName, Utils.cardHeight());
    }
}
