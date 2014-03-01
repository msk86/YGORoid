package org.msk86.ygoroid.size;

public class Size {
    int width, height;
    boolean positive = true;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Size rotate() {
        Size size = new Size(height, width);
        size.positive = !positive;
        return size;
    }

    @Override
    public String toString() {
        return width + "*" + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size = (Size) o;

        if (height != size.height) return false;
        if (width != size.width) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
