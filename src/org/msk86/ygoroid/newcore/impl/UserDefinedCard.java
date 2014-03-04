package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.UserDefinedCardGenerator;
import org.msk86.ygoroid.utils.Configuration;

public class UserDefinedCard extends Card {
    private String fileName;

    public UserDefinedCard(String name) {
        super("0", name, "");
        fileName = Configuration.userDefinedCardImgPath() + name + Configuration.cardImageSuffix();
    }

    public String getFileName() {
        return fileName;
    }

    BmpGenerator bmpGenerator;
    @Override
    public BmpGenerator getBmpGenerator() {
        if(bmpGenerator == null) {
            bmpGenerator = new UserDefinedCardGenerator(this);
        }
        return bmpGenerator;
    }

    @Override
    public UserDefinedCard clone() {
        return new UserDefinedCard(getName());
    }
}
