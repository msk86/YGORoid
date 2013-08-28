package org.msk86.ygoroid.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.ygo.R;
import org.msk86.ygoroid.YGOActivity;
import org.msk86.ygoroid.utils.Utils;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpgradeChecker implements Checker {

    public static final String UPGRADE_CHECK_URL = Utils.s(R.string.upgrade_check);

    private YGOActivity context;

    private JSONObject upgradeInfo = null;

    private Version myVersion;
    private Version latestVersion;
    private String upgradeUrl;
    private String upgradeDesc;

    public UpgradeChecker(YGOActivity context) {
        this.context = context;
    }

    public boolean checkUpgrade() {
        try {
            upgradeInfo = new JSONObject(NetClient.request(UPGRADE_CHECK_URL));
            latestVersion = findVersion();
            myVersion = new Version(Utils.getVersion());
            if (shouldUpgrade()) {
                upgradeUrl = upgradeInfo.getString("upgrade_url");
                upgradeDesc = upgradeInfo.getString("desc").replace("\\n", "\n");
                context.getUpgradeMsgHandler().sendEmptyMessage(UPGRADE);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private Version findVersion() {
        try {
            return new Version(this.upgradeInfo.getString("latest_version"));
        } catch (Exception e) {
            return new Version("0");
        }
    }

    private boolean shouldUpgrade() {
        return myVersion.version < latestVersion.version;
    }

    public void download() {
        Uri uri = Uri.parse(upgradeUrl);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    public void upgrade() {
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(String.format(Utils.s(R.string.NEW_VERSION), latestVersion.ver))
                .setMessage(upgradeDesc)
                .setPositiveButton(Utils.s(R.string.CONFIRM_YES), new OnUpgradeClickListener("OK"))
                .setNegativeButton(Utils.s(R.string.CONFIRM_NO), new OnUpgradeClickListener("Cancel"))
                .create();
        dialog.show();
    }

    private class OnUpgradeClickListener implements DialogInterface.OnClickListener {
        private String button;

        private OnUpgradeClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (button.equals("OK")) {
                download();
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
