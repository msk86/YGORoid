package org.msk86.ygoroid.utils;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class TextUtils {
    public static String cutOneLine(String str, TextPaint textPaint, int width) {
        StaticLayout layout = new StaticLayout(str, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        if (layout.getLineCount() > 1) {
            str = str.substring(0, layout.getLineEnd(0)).trim();
        }
        return str;
    }

    public static String cutPages(String str, int page, TextPaint textPaint, int width, int height) {
        StaticLayout layout = new StaticLayout(str, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        int lineCount = layout.getLineCount();
        int linePerPage = lineCount;
        for (int i = 0; i < lineCount; i++) {
            if (layout.getLineBottom(i) >= height) {
                linePerPage = i;
                break;
            }
        }
        int pages = (int) Math.ceil(lineCount * 1.0 / linePerPage);
        page = page % pages;
        int pageLineStart = page * linePerPage;
        int pageLineEnd = (page + 1) * linePerPage;
        pageLineEnd = pageLineEnd >= lineCount ? lineCount : pageLineEnd;
        str = str.substring(layout.getLineStart(pageLineStart), layout.getLineStart(pageLineEnd)).trim();
        return str;
    }
}
