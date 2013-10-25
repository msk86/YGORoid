package org.msk86.ygoroid.upgrade;

import org.msk86.ygoroid.YGOActivity;

public interface DownloaderCallback {
    public void callback(int success, int fail, int all);
}
