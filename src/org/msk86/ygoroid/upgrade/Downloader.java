package org.msk86.ygoroid.upgrade;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Downloader {

    private YGOActivity context;
    private DownloaderCallback successCallback;

    public Downloader(YGOActivity context) {
        this.context = context;
    }

    private int success = 0;
    private int fail = 0;
    private List<Task> tasks = new ArrayList<Task>();

    public void clear() {
        success = 0;
        fail = 0;
        tasks.clear();
        successCallback = null;
    }

    public void addTask(String remote, String path, String fileName, DownloadProgress downloadProgress) {
        tasks.add(new Task(remote, path, fileName, downloadProgress));
    }

    public void startDownload() {
        startDownload(null);
    }

    public void startDownload(final String infoTemplate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Task task : tasks) {
                    try {
                        downFile(task.remote, task.path, task.fileName, task.downloadProgress);
                        success++;
                        if (infoTemplate != null) {
                            context.showInfo(String.format(infoTemplate, success, tasks.size()));
                        }
                    } catch (IOException e) {
                        fail++;
                    }
                    task.clear();
                }
                if (successCallback != null) {
                    successCallback.callback(success, fail, tasks.size());
                }
                tasks.clear();
            }
        }).start();
    }

    public void downFile(String remote, String path, String fileName, DownloadProgress downloadProgress)
            throws IOException {
        if (downloadProgress == null) {
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
        };
    }

    public void successCallback(DownloaderCallback downloaderCallback) {
        this.successCallback = downloaderCallback;
    }

    private static class Task {
        private String remote;
        private String path;
        private String fileName;
        private DownloadProgress downloadProgress;

        public Task(String remote, String path) {
            this(remote, path, null, null);
        }

        public Task(String remote, String path, String fileName, DownloadProgress downloadProgress) {
            this.remote = remote;
            this.path = path;
            this.fileName = fileName;
            this.downloadProgress = downloadProgress;

            if (this.fileName == null || this.fileName.length() == 0) {
                this.fileName = this.remote.substring(this.remote.lastIndexOf("/") + 1);
            }
            if (downloadProgress == null) {
                this.downloadProgress = defaultDownloadProgress();
            }
        }

        private DownloadProgress defaultDownloadProgress() {
            return new DownloadProgress() {
                public void display(YGOActivity context, String file, long fileSize, long progress) {
                }
            };
        }

        public void clear() {
            remote = null;
            path = null;
            fileName = null;
            downloadProgress = null;
        }

    }
}
