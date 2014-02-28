package org.msk86.ygoroid.size;

import org.msk86.ygoroid.utils.Utils2;

public enum TotalSize implements Size {
    SCREEN {
        @Override
        public int width() {
            return Utils2.screenWidth();
        }

        @Override
        public int height() {
            return Utils2.screenHeight();
        }
    },

    TOTAL {
        @Override
        public int width() {
            return FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2;
        }

        @Override
        public int height() {
            return FieldSize.SQUARE.height() * 4;
        }
    },

    HAND_CARDS {
        @Override
        public int width() {
            return FieldSize.SQUARE.width() * 5 - CardSize.NORMAL.width() / 2;
        }

        @Override
        public int height() {
            return CardSize.NORMAL.height();
        }
    };

    public int width() {
        return this.width();
    }

    public int height() {
        return this.height();
    }

}
