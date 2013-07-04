package android.ygo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipReader {

    public static InputStream readZipFile(String zip, String innerFileName) {
        ZipFile innerZipFile = null;
        try {
            innerZipFile = new ZipFile(zip);
            Enumeration entries = innerZipFile.entries();
            ZipEntry entryIn = null;
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry);
                if (entry.getName().compareToIgnoreCase(innerFileName) == 0) {
                    entryIn = entry;
                    break;
                }
            }

            if (entryIn != null) {
                return innerZipFile.getInputStream(entryIn);
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
