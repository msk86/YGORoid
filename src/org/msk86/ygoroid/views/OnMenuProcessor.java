package org.msk86.ygoroid.views;

import android.view.Menu;
import android.view.MenuItem;

public interface OnMenuProcessor {
    public boolean onMenuPrepare(Menu menu);
    public boolean onMenuClick(MenuItem menuItem);
}
