package android.ygo.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.ygo.YGOActivity;
import android.ygo.core.Card;
import android.ygo.core.Drawable;
import android.ygo.sqlite.CardsDBHelper;
import android.ygo.views.DuelDiskView;

import java.io.*;
import java.util.List;
import java.util.Random;

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
        checkFolder(Configuration.screenShotPath(), false);
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

    public static int unitLength() {
        int longer = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        int shorter = dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels;
        int unitLengthW = (int) (longer / 6f);
        int unitLengthH = (int) (shorter / 3.95f);
        return unitLengthW < unitLengthH ? unitLengthW : unitLengthH;
    }

    public static int totalWidth() {
        return unitLength() * 6;
    }

    public static int cardHeight() {
        int padding = 2;
        return unitLength() - padding * 2;
    }

    public static int cardWidth() {
        return (int) (cardHeight() / 1.45);
    }

    public static Bitmap readBitmapScaleByHeight(int resId, int targetHeight) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
            return scaleByHeight(bitmap, targetHeight);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readBitmapScaleByHeight(String file, int targetHeight) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            return scaleByHeight(bitmap, targetHeight);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap readBitmapScaleByHeight(String zip, String file, int targetHeight) {
        try {
            InputStream is = ZipReader.readZipFile(zip, file);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return scaleByHeight(bitmap, targetHeight);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap scaleByHeight(Bitmap bitmap, int targetHeight) {
        Matrix matrix = new Matrix();

        float changeRate = targetHeight * 1.0f / bitmap.getHeight();
        matrix.postScale(changeRate, changeRate);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return newBitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(degree);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return newBitmap;
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
        canvas.drawBitmap(bitmap, posX, posY, paint);
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
            canvas.drawBitmap(bitmap, x + left, y + top, paint);
        }

        public void drawDrawable(Canvas canvas, Drawable drawable, int left, int top) {
            drawable.draw(canvas, x + left, y + top);
        }

        public void drawLayout(Canvas canvas, Layout layout, int left, int top) {
            canvas.translate(x + left, y + top);
            layout.draw(canvas);
            canvas.translate(-x - left, -y - top);
        }
    }


    public static void shuffle(List<Card> cards) {
        Random random = new Random();
        for (int i = 1; i < cards.size(); i++) {
            swapCard(cards, i, random.nextInt(i));
        }
    }

    private static void swapCard(List<Card> cards, int indexA, int indexB) {
        Card temp = cards.get(indexA);
        cards.set(indexA, cards.get(indexB));
        cards.set(indexB, temp);
    }

    public static String screenShot() {
        DuelDiskView v = context.getDuelDiskView();
        Bitmap bitmap = Bitmap.createBitmap(Utils.screenWidth(), Utils.screenHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.drawDuelDisk(canvas);

        String fileName = System.currentTimeMillis() + ".jpg";
        String filePath = Configuration.screenShotPath() + fileName;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return fileName;
        } catch (Exception e) {
            return null;
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

    public static String cutOneLine(String str, TextPaint textPaint, int width) {
        StaticLayout layout = new StaticLayout(str, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        if (layout.getLineCount() > 1) {
            str = str.substring(0, layout.getLineEnd(0)).trim();
        }
        return str;
    }
}
