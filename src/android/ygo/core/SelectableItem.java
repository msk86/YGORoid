package android.ygo.core;

public interface SelectableItem extends Item {
    public void select();

    public void unSelect();

    public boolean isSelect();
}
