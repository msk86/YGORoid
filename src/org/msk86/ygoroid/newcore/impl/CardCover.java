package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.Bmpable;
import org.msk86.ygoroid.newcore.impl.bmp.CardCoverGenerator;

public class CardCover implements Bmpable {

    private CardCover() {}

    public static CardCover getInstance() {
        return new CardCover();
    }

    private BmpGenerator generator;
    @Override
    public BmpGenerator getBmpGenerator() {
        if(generator == null) {
            generator = new CardCoverGenerator();
        }
        return generator;
    }
}
