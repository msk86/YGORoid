package org.msk86.ygoroid.upgrade;

import org.msk86.ygoroid.YGOActivity;

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
    private DownloaderCallback progressCallback;

    public Downloader(YGOActivity context) {
        this.context = context;
    }

    private int threads = 1;
    private int doneCount = 0;
    private int success = 0;
    private int fail = 0;
    private List<Task> tasks = new ArrayList<Task>();

    public void clear() {
        threads = 1;
        success = 0;
        fail = 0;
        tasks.clear();
        successCallback = null;
    }

    public void setThreads(int threads) {
        if (threads > 0) {
            this.threads = threads;
        }
    }

    public void addTask(String remote, String path, String fileName, DownloadProgress downloadProgress) {
        tasks.add(new Task(remote, path, fileName, downloadProgress));
    }

    public void startDownload() {
        List<List<Task>> subTasksList = new ArrayList<List<Task>>();
        for (int i = 0; i < threads; i++) {
            subTasksList.add(new ArrayList<Task>());
        }

        for (int i = 0; i < tasks.size(); i++) {
            subTasksList.get(i % threads).add(tasks.get(i));
        }

        for (List<Task> subTasks : subTasksList) {
            if (subTasks.size() > 0) {
                new Thread(new DownloadThread(subTasks)).start();
            }
        }
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

    public void progressCallback(DownloaderCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    private static class Task {
        private String remote;
        private String path;
        private String fileName;
        private DownloadProgress downloadProgress;

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

    private class DownloadThread implements Runnable {
        private List<Task> subTasks;

        public DownloadThread(List<Task> subTasks) {
            this.subTasks = subTasks;
        }

        @Override
        public void run() {
            for (Task task : subTasks) {
                try {
                    downFile(task.remote, task.path, task.fileName, task.downloadProgress);
                    success++;
                    if (progressCallback != null) {
                        progressCallback.callback(success, fail, tasks.size());
                    }
                } catch (IOException e) {
                    fail++;
                }
                task.clear();
            }
            doneCount++;
            if (doneCount == threads && successCallback != null) {
                successCallback.callback(success, fail, tasks.size());
            }
            subTasks.clear();
        }
    }
}
