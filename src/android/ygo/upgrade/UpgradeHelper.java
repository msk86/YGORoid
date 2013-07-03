package android.ygo.upgrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

    private static final String REMOTE_URL =
            "https://www.evernote.com/shard/s315/sh/403e5aba-86cd-4975-9854-6170bf12934f/5d4939bbe181fb8be46be192f74bd266";

    private YGOActivity context;

    private Version myVersion;
    private Version newVersion;
    private String upgradeUrl;

    public UpgradeHelper(YGOActivity context) {
        this.context = context;
    }

    public void startDetect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String versionHtml = versionHtml();
                if(versionHtml == null) {
                    return;
                }
                newVersion = findVersion(versionHtml);
                String myVersionStr = Utils.getVersion();
                myVersion = new Version(myVersionStr);
                upgradeUrl = findUpgradeUrl(versionHtml);

                if(myVersion.version < newVersion.version) {
                    context.getUpgradeMsgHandler().sendEmptyMessage(0);
                }
            }
        }).start();
    }

    public void alertUpgrade() {
        if(newVersion == null) {
            return;
        }
        String title = "发现新版本程序[V" + newVersion.ver + "]，是否更新？";
        AlertDialog dialog = new AlertDialog.Builder(Utils.getContext())
                .setTitle(title)
                .setPositiveButton("确定", new OnUpgradeClickListener("OK", this))
                .setNegativeButton("取消", new OnUpgradeClickListener("Cancel", this))
                .create();
        dialog.show();
    }

    public void download() {
        Uri uri = Uri.parse(upgradeUrl);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    private static String versionHtml() {
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
        } catch (Exception e) {}
        if(remoteHtml.length() == 0) {
            return null;
        }
        String html = remoteHtml.toString();
        return html.substring(html.indexOf("</head>") + 7);
    }

    private static Version findVersion(String str) {
        Pattern pattern = Pattern.compile("RoidVersion:\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            return new Version(matcher.group(1));
        } else {
            return new Version("0");
        }
    }

    private static String findUpgradeUrl(String str) {
        Pattern pattern = Pattern.compile("RoidUpgradeUrl:\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            return matcher.group(1).replace("&amp;", "&");
        } else {
            return null;
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
            if(button.equals("OK")) {
                helper.download();
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
            if(matcher.find()) {
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
