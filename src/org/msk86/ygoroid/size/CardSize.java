package org.msk86.ygoroid.size;

import org.msk86.ygoroid.utils.Utils2;

public enum CardSize implements Size {
    NORMAL {
        @Override
        public int width() {
            return (int) (height() / 1.45);
        }

        @Override
        public int height() {
            int medianH = Utils2.screenHeight() / 4 - Utils2.commonPadding() * 2;
            int medianW = (int) ((Utils2.screenWidth() - 14 * Utils2.commonPadding()) * 1.45 / 9.25);
            return medianW < medianH ? medianW : medianH;
        }
    },

    SCREEN {
        @Override
        public int width() {
            return (int) (height() / 1.45);
        }

        @Override
        public int height() {
            return Utils2.screenHeight();
        }
    },

    SNAPSHOT {
        @Override
        public int width() {
            return (int) (height() / 1.45);
        }

        @Override
        public int height() {
            return NORMAL.height() * 3 / 4;
        }
    },

    PREVIEW {
        @Override
        public int width() {
            return Utils2.screenWidth() / 4;
        }

        @Override
        public int height() {
            return (int)(width() * 1.45);
        }
    };

    public int width() {
        return this.width();
    }

    public int height() {
        return this.height();
    }
}
