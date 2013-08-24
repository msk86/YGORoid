package android.ygo.upgrade;

public interface Checker {

    public static final int UPGRADE = 0;
    public static final int DATA_BASE = 1;
    public static final int PICS = 2;

    public boolean checkUpgrade();

    public void upgrade();
}
