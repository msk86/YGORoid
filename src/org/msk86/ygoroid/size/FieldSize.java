package org.msk86.ygoroid.size;

import org.msk86.ygoroid.utils.Style;

public enum FieldSize implements Size {
    SQUARE {
        @Override
        public int width() {
            return CardSize.NORMAL.height() + Style.padding() * 2;
        }

        @Override
        public int height() {
            return width();
        }
    },

    RECT {
        @Override
        public int width() {
            return CardSize.NORMAL.width() + Style.padding() * 2;
        }

        @Override
        public int height() {
            return CardSize.NORMAL.height() + Style.padding() * 2;
        }
    }  ;

    public int width() {
        return this.width();
    }

    public int height() {
        return this.height();
    }

}
