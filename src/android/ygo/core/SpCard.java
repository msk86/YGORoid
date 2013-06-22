package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.R;
import android.ygo.utils.Utils;

public class SpCard extends Card {
    private int resId;
    public SpCard(String id, String name, String desc, int resId) {
        super(id, name, desc);
        this.resId = resId;
        super.cardPic = Utils.readBitmapScaleByHeight(resId, Utils.cardHeight());

    }

    public static SpCard createDeveloper() {
        return new SpCard("-1000", "About", "Developer: Msk86, Email: msk86.kaiser@gmail.com", R.raw.dev_msk86);
    }

    @Override
    public Bitmap bigCardPic() {
        return  Utils.readBitmapScaleByHeight(resId, Utils.screenHeight());
    }
}
