package org.msk86.ygoroid.newutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Layout;
import android.util.DisplayMetrics;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.newsqlite.CardsDBHelper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class Utils {
    private static DisplayMetrics dm;
    private static YGOActivity context;
    private static CardsDBHelper dbHelper;

    public static void initInstance(YGOActivity activity) {
        context = activity;
        dm = new DisplayMetrics();
        dbHelper = new CardsDBHelper(activity, 1);
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        checkFolders();
    }

    private static void checkFolders() {
        checkFolder(Configuration.baseDir(), false);
        checkFolder(Configuration.deckPath(), false);
        checkFolder(Configuration.cardImgPath(), true);
        checkFolder(Configuration.userDefinedCardImgPath(), true);
        checkFolder(Configuration.texturePath(), true);
    }

    private static void checkFolder(String path, boolean noMedia) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (noMedia) {
            File noMediaDir = new File(path + ".nomedia");
            if (!noMediaDir.exists()) {
                noMediaDir.mkdirs();
            }
        }
    }

    public static YGOActivity getContext() {
        return context;
    }

    public static CardsDBHelper getDbHelper() {
        return dbHelper;
    }

    public static int screenHeight() {
        return dm.heightPixels;
    }

    public static int screenWidth() {
        return dm.widthPixels;
    }

    public static int commonPadding() {
        return 2;
    }

    public static int deckBuilderWidth() {
        return screenWidth() * 3 / 4;
    }

    public static String tempifyDeck(String deck) {
        return deck + "._tmp";
    }

    public static boolean isTempDeck(String deck) {
        return deck.endsWith("._tmp");
    }

    public static String untempifyDeck(String tempDeck) {
        if (isTempDeck(tempDeck)) {
            return tempDeck.substring(0, tempDeck.length() - 5);
        }
        return tempDeck;
    }

    public static void clearAllTempDeck() {
        File deckPath = new File(Configuration.deckPath());
        File[] tempDecks = deckPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return isTempDeck(name);
            }
        });
        for (File tempDeck : tempDecks) {
            tempDeck.delete();
        }
    }

    public static String findTempDeck() {
        File deckPath = new File(Configuration.deckPath());
        String[] tempDecks = deckPath.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return isTempDeck(name);
            }
        });
        return tempDecks.length == 1 ? tempDecks[0] : null;
    }

    public static String[] decks() {
        File deckPath = new File(Configuration.deckPath());
        return deckPath.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return !isTempDeck(name);
            }
        });
    }

    public static String[] cardPicZips() {
        File picsDir = new File(Configuration.cardImgPath());
        String[] zips = picsDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.startsWith("pics") && name.endsWith(".zip");
            }
        });
        Arrays.sort(zips, new Comparator<String>() {
            @Override
            public int compare(String zip1, String zip2) {
                if (zip1.length() != zip2.length()) {
                    return zip2.length() - zip1.length();
                }
                for (int i = 0; i < zip1.length(); i++) {
                    if (zip1.charAt(i) != zip2.charAt(i)) {
                        return zip2.charAt(i) - zip1.charAt(i);
                    }
                }
                return 0;
            }
        });
        return zips;
    }

    public static String[] cardPics() {
        File picsDir = new File(Configuration.cardImgPath());
        return picsDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".jpg");
            }
        });
    }

    public static int countPics() {
        File picsDir = new File(Configuration.cardImgPath());
        String[] pics = picsDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".jpg");
            }
        });
        return pics.length;
    }

    public static final int DRAW_POSITION_FIRST = -0x1000;
    public static final int DRAW_POSITION_CENTER = -0x2000;
    public static final int DRAW_POSITION_LAST = -0x3000;

    public static void drawBitmapOnCanvas(Canvas canvas, Bitmap bitmap, Paint paint, int positionX, int positionY) {
        int posX = 0;
        int posY = 0;
        switch (positionX) {
            case DRAW_POSITION_FIRST:
                posX = 0;
                break;
            case DRAW_POSITION_CENTER:
                posX = (canvas.getWidth() - bitmap.getWidth()) / 2;
                break;
            case DRAW_POSITION_LAST:
                posX = canvas.getWidth() - bitmap.getWidth();
                break;
            default:
                posX = positionX;
        }
        switch (positionY) {
            case DRAW_POSITION_FIRST:
                posY = 0;
                break;
            case DRAW_POSITION_CENTER:
                posY = (canvas.getHeight() - bitmap.getHeight()) / 2;
                break;
            case DRAW_POSITION_LAST:
                posY = canvas.getHeight() - bitmap.getHeight();
                break;
            default:
                posY = positionY;
        }
        if (!bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, posX, posY, paint);
        }
    }

    public static void deleteDeck(String deckName) {
        File deckFile = new File(Configuration.deckPath() + deckName);
        if (deckFile.isFile() && deckFile.exists()) {
            deckFile.delete();
        }
    }

    public static class DrawHelper {
        private int x;
        private int y;

        public DrawHelper(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int center(int frameSize, int size) {
            return (frameSize - size) / 2;
        }

        public int bottom(int frameSize, int size) {
            return frameSize - size;
        }

        public void drawText(Canvas canvas, String str, int left, int top, Paint paint) {
            canvas.drawText(str, x + left, y + top, paint);
        }

        public void drawLine(Canvas canvas, int startX, int startY, int stopX, int stopY, Paint paint) {
            canvas.drawLine(x + startX, y + startY, x + stopX, y + stopY, paint);
        }

        public void drawCircle(Canvas canvas, int left, int top, int radius, Paint paint) {
            canvas.drawCircle(x + left, y + top, radius, paint);
        }

        public void drawRect(Canvas canvas, Rect r, Paint paint) {
            r.offset(x, y);
            canvas.drawRect(r, paint);
        }

        public void drawRoundRect(Canvas canvas, RectF rect, int rx, int ry, Paint paint) {
            rect.offset(x, y);
            canvas.drawRoundRect(rect, rx, ry, paint);
        }

        public void drawBitmap(Canvas canvas, Bitmap bitmap, int left, int top, Paint paint) {
            if (!bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, x + left, y + top, paint);
            }
        }

        public void drawLayout(Canvas canvas, Layout layout, int left, int top) {
            canvas.translate(x + left, y + top);
            layout.draw(canvas);
            canvas.translate(-x - left, -y - top);
        }
    }

    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "0";
        }
    }

    public static String s(int id) {
        return context.getResources().getString(id);
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}
