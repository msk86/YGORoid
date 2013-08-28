package android.ygo.upgrade;

import android.ygo.YGOActivity;

public interface DownloadProgress {
    public void display(YGOActivity context, String file, long fileSize, long progress);
}
