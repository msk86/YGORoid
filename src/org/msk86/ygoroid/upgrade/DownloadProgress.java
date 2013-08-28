package org.msk86.ygoroid.upgrade;

import org.msk86.ygoroid.YGOActivity;

public interface DownloadProgress {
    public void display(YGOActivity context, String file, long fileSize, long progress);
}
