package android.ygo.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipReader {

    public static void extractZipFile(String zip, String innerFileName, String extractFileName) {
        ZipFile innerZipFile = null;
        try {
            innerZipFile = new ZipFile(zip);
            Enumeration entries = innerZipFile.entries();
            ZipEntry entryIn = null;
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.getName().compareToIgnoreCase(innerFileName) == 0) {
                    entryIn = entry;
                    break;
                }
            }

            if (entryIn != null) {
                InputStream inputStream = innerZipFile.getInputStream(entryIn);
                extract(inputStream, extractFileName);
            }
        } catch (IOException e) {
        }
    }

    private static void extract(InputStream is, String fileName) {
        FileOutputStream os = null;
        byte[] buffer = new byte[4096];
        int bytes_read;
        try {
            os = new FileOutputStream(fileName);

            while ((bytes_read = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes_read);
            }
            os.close();
            is.close();
        } catch (Exception e) {
        }
    }
}
