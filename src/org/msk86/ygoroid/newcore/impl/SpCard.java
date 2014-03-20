package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.SpCardGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.UserDefinedCardGenerator;
import org.msk86.ygoroid.newutils.Configuration;
import org.msk86.ygoroid.newutils.Utils;

public class SpCard extends Card {
    int resId;
    public SpCard(String id, String name, String desc, int resId) {
        super(id, name, desc);
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public static SpCard createMsk86() {
        return new SpCard("-1000", "Msk86.kaiser@gmail.com:", "Developer", R.raw.sp_msk86);
    }

    public static SpCard createZh99998() {
        return new SpCard("-1000", "Zh99998@gmail.com:", "Resource Provider", R.raw.sp_zh99998);
    }

    public static SpCard createHeaven() {
        return new SpCard("-1000", "20650688@qq.com:", "Group Manager", R.raw.sp_heaven);
    }

    public static SpCard createQuickStart() {
        return new SpCard("-1000", "Help:", Utils.s(R.string.startup_hint), R.raw.sp_help);
    }


    BmpGenerator bmpGenerator;
    @Override
    public BmpGenerator getBmpGenerator() {
        if(bmpGenerator == null) {
            bmpGenerator = new SpCardGenerator(this);
        }
        return bmpGenerator;
    }
}
