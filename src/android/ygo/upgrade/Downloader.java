package android.ygo.upgrade;

import android.ygo.YGOActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {

    private YGOActivity context;

    public Downloader(YGOActivity context) {
        this.context = context;
    }

    public void downFile(String remote, String path, String fileName, DownloadProgress downloadProgress)
            throws IOException {
        if(downloadProgress == null) {
            downloadProgress = defaultDownloadProgress();
        }
        if (fileName == null || fileName.length() == 0) {
            fileName = remote.substring(remote.lastIndexOf("/") + 1);
        }

        URL url = new URL(remote);
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        long fileSize = conn.getContentLength();
        if (is == null) {
            downloadProgress.error(context, "无法获取远端文件[" + remote + "]");
            return;
        }

        long downLoadFilePosition = 0;
        FileOutputStream fos = new FileOutputStream(path + fileName + ".tmp");
        byte buf[] = new byte[1024];

        int numRead;

        downloadProgress.display(context, fileName, fileSize, downLoadFilePosition);
        while ((numRead = is.read(buf)) != -1) {
            fos.write(buf, 0, numRead);
            downLoadFilePosition += numRead;
            downloadProgress.display(context, fileName, fileSize, downLoadFilePosition);
        }

        File targetFile = new File(path + fileName);
        File tmpFile = new File(path + fileName + ".tmp");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        tmpFile.renameTo(targetFile);

        try {
            is.close();
        } catch (Exception ex) {
        }

    }

    private DownloadProgress defaultDownloadProgress() {
        return new DownloadProgress() {
            public void display(YGOActivity context, String file, long fileSize, long progress) {
            }

            public void error(YGOActivity context, String info) {
                context.showInfo(info);
            }
        };
    }
}
