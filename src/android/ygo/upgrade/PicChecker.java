package android.ygo.upgrade;

import android.ygo.YGOActivity;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PicChecker implements Checker {

    private YGOActivity context;

    private Set<String> missingIds;

    public PicChecker(YGOActivity context) {
        this.context = context;
    }

    @Override
    public boolean checkUpgrade() {
        context.getUpgradeMsgHandler().sendEmptyMessage(Checker.PICS);
        return true;
    }

    private void getMissingIds() {
        Set<String> cardIdInFile = new HashSet<String>(10000);

        Set<String> cardPics = new HashSet<String>(10000);

        String[] pics = Utils.cardPics();
        Collections.addAll(cardPics, pics);

        String[] zips = Utils.cardPicZips();
        for (String zip : zips) {
            String[] picsInZip = Utils.cardPicsInZip(Configuration.cardImgPath() + zip);
            Collections.addAll(cardPics, picsInZip);
        }

        for (String pic : cardPics) {
            String id = pic.substring(pic.lastIndexOf('/') + 1, pic.lastIndexOf('.'));
            cardIdInFile.add(id);
        }

        Set<String> cardIdInDB = Utils.getDbHelper().getAllCardId();

        cardIdInDB.removeAll(cardIdInFile);
        missingIds = cardIdInDB;
    }

    private boolean shouldUpgrade() {
        return missingIds.size() > 0;
    }

    @Override
    public void upgrade() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                waitForDatabase();

                getMissingIds();
                if (!shouldUpgrade()) {
                    return;
                }

                int count = 0;
                for (String id : missingIds) {
                    count++;
                    String url = "http://images.my-card.in/" + id + ".jpg";
                    context.showInfo("正在更新卡图[" + count + "/" + missingIds.size() + "]...");
                    try {
                        context.getDownloader().downFile(url, Configuration.cardImgPath(), null, null);
                    } catch (IOException e) {
                    }
                }
                context.showInfo("卡图更新完成.");
            }

            private void waitForDatabase() {
                while(!context.isNewestDatabase()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }
}
