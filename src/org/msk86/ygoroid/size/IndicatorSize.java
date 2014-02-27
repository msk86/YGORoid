package org.msk86.ygoroid.size;

public enum IndicatorSize implements Size {
    NORMAL {
        @Override
        public int width() {
            return CardSize.NORMAL.width() / 4;
        }

        @Override
        public int height() {
            return width();
        }
    };

    public int width() {
        return this.width();
    }

    public int height() {
        return this.height();
    }
}
