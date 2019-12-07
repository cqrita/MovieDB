package com.example.MovieDB.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;

public class IMDBNetwork extends AsyncTask<Void, Void, String> {
    private String url;
    private HashMap<String, String> params;
    private HttpCallback httpCallback;

    public IMDBNetwork(String url, HttpCallback httpCallback) {
        this.url = url;
        this.httpCallback = httpCallback;
    }
    @Override
    protected String doInBackground(Void... args) {
        String response = "";
        String postData = "";

        PrintWriter pw = null;
        BufferedReader in = null;

        try {
            URL text = new URL(url);
            HttpURLConnection http = (HttpURLConnection) text.openConnection();
            http.setRequestMethod("POST");
            http.setConnectTimeout(15 * 1000);
            http.setReadTimeout(15 * 1000);
            http.setDoInput(true);
            http.setDoOutput(true);

            Log.d("woonse", postData);

            pw = new PrintWriter(new OutputStreamWriter( http.getOutputStream(), "UTF-8"));
            pw.write(postData);
            pw.flush();

            in = new BufferedReader(new InputStreamReader( http.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            response = URLDecoder.decode(sb.toString(), "UTF-8");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(this.httpCallback != null) {
            this.httpCallback.onResult(s);
        }
    }

    public interface HttpCallback {
        void onResult(String result);
    }
}
