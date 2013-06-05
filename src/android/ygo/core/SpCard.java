package android.ygo.core;

import android.ygo.R;
import android.ygo.utils.Utils;

public class SpCard extends Card {
    public SpCard(String id, String name, String desc, int resId) {
        super(id, name, desc);
        super.cardPic = Utils.readBitmapScaleByHeight(resId, Utils.cardHeight());
    }

    public static SpCard createDeveloper() {
        return new SpCard("-1000", "About", "Developer: Msk86, Email: msk86.kaiser@gmail.com", R.raw.dev_msk86);
    }
}
