package org.msk86.ygoroid.upgrade;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetClient {
    public static String request(String url) {
        StringBuilder remoteHtml = new StringBuilder();
        BufferedReader reader;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                reader = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent()));
                String s;
                while ((s = reader.readLine()) != null) {
                    remoteHtml.append(s);
                }
                reader.close();
                return remoteHtml.toString();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
