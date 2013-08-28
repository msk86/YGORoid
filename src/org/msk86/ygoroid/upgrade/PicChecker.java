package org.msk86.ygoroid.upgrade;

import android.ygo.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

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
        context.getUpgradeMsgHandler().sendEmptyMessage(PICS);
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
                download();
            }
        }).start();
    }

    private void waitForDatabase() {
        while (!context.isNewestDatabase()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }


    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                int failCount = 0;
                for (String id : missingIds) {
                    count++;
                    String url = String.format(Utils.s(R.string.pics_api), id);
                    context.showInfo(String.format(Utils.s(R.string.pics_updating), count, missingIds.size()));
                    try {
                        context.getDownloader().downFile(url, Configuration.cardImgPath(), null, null);
                    } catch (IOException e) {
                        failCount++;
                    }
                }
                String info = Utils.s(R.string.pics_updated);
                if (failCount > 0) {
                    info += String.format(Utils.s(R.string.pics_update_failed), failCount);
                }
                context.showInfo(info);
            }
        }).start();
    }
}
