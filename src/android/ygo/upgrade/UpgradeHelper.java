package android.ygo.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.ygo.R;
import android.ygo.YGOActivity;
import android.ygo.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpgradeHelper {

    public static final int UPGRADE = 0;
    public static final int NEW_CARDS = 1;
    public static final int ALL_RESOURCES = 2;

    private static final String REMOTE_URL =
            "https://www.evernote.com/shard/s315/sh/403e5aba-86cd-4975-9854-6170bf12934f/5d4939bbe181fb8be46be192f74bd266";

    private YGOActivity context;

    private String upgradeInfo = null;

    private Version myVersion;
    private Version newVersion;
    private String upgradeUrl;
    private String upgradeTip;

    private String newCardsFile;
    private String newCardsUrl;
    private String newCardsGroup;

    private String allResourcesUrl;

    public UpgradeHelper(YGOActivity context) {
        this.context = context;
    }

    public void startDetect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchUpgradeInfo();
                if (upgradeInfo == null) {
                    return;
                }

                newVersion = findVersion();
                String myVersionStr = Utils.getVersion();
                myVersion = new Version(myVersionStr);
                upgradeUrl = getInfo("RoidUpgradeUrl");
                upgradeTip = getInfo("RoidUpgradeTip").replace("\\n", "\n");

                if (myVersion.version < newVersion.version) {
                    context.getUpgradeMsgHandler().sendEmptyMessage(UPGRADE);
                    return;
                }

                newCardsFile = getInfo("RoidNewCardFile");
                String totalPicCount = getInfo("RoidTotalCardPics");
                newCardsUrl = getInfo("RoidNewCardUrl");
                newCardsGroup = getInfo("RoidNewCardGroup");

                allResourcesUrl = getInfo("RoidAllResources");

                if (noResource()) {
                    context.getUpgradeMsgHandler().sendEmptyMessage(ALL_RESOURCES);
                } else if (!detectFile(newCardsFile, totalPicCount)) {
                    context.getUpgradeMsgHandler().sendEmptyMessage(NEW_CARDS);
                }

            }
        }).start();
    }

    public void alertUpgrade() {
        String title = String.format(Utils.s(R.string.NEW_VERSION), newVersion.ver);
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(title)
                .setMessage(upgradeTip)
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnUpgradeClickListener("OK", this))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnUpgradeClickListener("Cancel", this))
                .create();
        dialog.show();
    }

    public void alertNewCards() {
        String title = String.format(Utils.s(R.string.NEW_CARDS), newCardsGroup, newCardsFile);

        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(title)
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnNewCardsClickListener("OK", this))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnNewCardsClickListener("Cancel", this))
                .create();
        dialog.show();
    }

    public void alertAllResources() {
        String title = Utils.s(R.string.ALL_RESOURCE);

        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(title)
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnAllResourcesClickListener("OK", this))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnAllResourcesClickListener("Cancel", this))
                .create();
        dialog.show();
    }

    public void download(String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    private void fetchUpgradeInfo() {
        StringBuilder remoteHtml = new StringBuilder();
        BufferedReader reader;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet(REMOTE_URL);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                reader = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent()));
                String s;
                while ((s = reader.readLine()) != null) {
                    remoteHtml.append(s);
                }
                reader.close();
            }
        } catch (Exception e) {
        }
        if (remoteHtml.length() == 0) {
            return;
        }
        upgradeInfo = remoteHtml.toString();
        upgradeInfo = upgradeInfo.substring(upgradeInfo.indexOf("</head>") + 7);
    }

    private String getInfo(String key) {
        Pattern pattern = Pattern.compile(key + ":\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(upgradeInfo);
        if (matcher.find()) {
            return matcher.group(1).replace("&amp;", "&");
        } else {
            return null;
        }
    }

    private Version findVersion() {
        String version = getInfo("RoidVersion");
        if (version != null) {
            return new Version(version);
        }
        return new Version("0");
    }

    private boolean noResource() {
        String[] zips = Utils.cardPicZips();
        int picCount = Utils.countPics();

        if (zips.length == 0 && picCount == 0) {
            return true;
        }
        return false;
    }

    private boolean detectFile(String file, String picTotalCount) {
        String[] zips = Utils.cardPicZips();
        if (zips.length == 0) {
            int picCount = Utils.countPics();
            int totalCount = Integer.parseInt(picTotalCount);
            return picCount >= totalCount;
        } else {
            for (String zip : zips) {
                if (zip.equals(file)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class OnAllResourcesClickListener implements DialogInterface.OnClickListener {
        private String button;
        private UpgradeHelper helper;

        private OnAllResourcesClickListener(String button, UpgradeHelper helper) {
            this.button = button;
            this.helper = helper;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (button.equals("OK")) {
                helper.download(helper.allResourcesUrl);
            }
        }
    }

    public static class OnNewCardsClickListener implements DialogInterface.OnClickListener {
        private String button;
        private UpgradeHelper helper;

        private OnNewCardsClickListener(String button, UpgradeHelper helper) {
            this.button = button;
            this.helper = helper;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (button.equals("OK")) {
                helper.download(helper.newCardsUrl);
            }
        }
    }

    private static class OnUpgradeClickListener implements DialogInterface.OnClickListener {
        private String button;
        private UpgradeHelper helper;

        private OnUpgradeClickListener(String button, UpgradeHelper helper) {
            this.button = button;
            this.helper = helper;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (button.equals("OK")) {
                helper.download(helper.upgradeUrl);
            }
        }
    }

    private static class Version {
        private float version;
        private String suffix;
        private String ver;

        private Version(String ver) {
            Pattern pattern = Pattern.compile("([0-9.]+)(.*?)");
            Matcher matcher = pattern.matcher(ver);
            if (matcher.find()) {
                version = Float.parseFloat(matcher.group(1));
                suffix = matcher.group(2);
            } else {
                version = 0;
                suffix = "";
            }
            this.ver = "" + version + suffix;
        }
    }
}
