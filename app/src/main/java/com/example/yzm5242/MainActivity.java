package com.example.yzm5242;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if (isOnline()) {
            GetAnnouncementList task = new GetAnnouncementList();
            task.execute();
        }*/

        /*if (isOnline()) {
            ConcurrentTask t = new ConcurrentTask();
            t.doTheWork();
        }*/
    }

    private class GetAnnouncementList extends AsyncTask<Void, Void, String> {
        org.json.JSONObject jsonO;
        org.json.JSONObject jObj;
        private InputStream is = null;
        private String json = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Devam eden işleminiz bulunmaktadır. Lütfen bekleyiniz..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ClassJSONParser jParser = new ClassJSONParser();
            String FinalURL = "http://api.plos.org/search?q=title:DNA";
            jsonO = jParser.getJSONFromUrl(FinalURL);
            /*try {
                // defaultHttpClient
                URL urlObject = new java.net.URL("http://api.plos.org/search?q=title:DNA");
                HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
                conn.setRequestMethod("GET");
                is = new BufferedInputStream(conn.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8); // iso-8859-1 //
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String tmp = line + "/n";
                    sb.append(tmp);
                }
                is.close();
                json = sb.toString();
                Log.d("***jsonData", json);
            } catch (Exception e) {
                Log.i("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                if (json != null) {
                    jObj = new JSONObject(json);
                } else {
                    jObj = null;
                }

            } catch (JSONException e) {
                //
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("***JsonFromMethod", jsonO.toString());
            progressDialog.dismiss();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    public class ConcurrentTask {
        ExecutorService executor;

        public ConcurrentTask() {
            /* I used an executor that creates a new thread every time creating a server requests object
             * you might need to create a thread pool instead, depending on your application
             * */
            executor = Executors.newSingleThreadExecutor();
        }

        private JSONObject doTheWork() {
            // init
            Callable<JSONObject> callable;
            Future<JSONObject> future;
            JSONObject jsonResult = null;

            try {
                // create callable object with desired job
                callable = new Callable<JSONObject>() {
                    @Override
                    public JSONObject call() throws Exception {
                        JSONObject jsonResponse;

                        // connect to the server
                        ClassJSONParser jParser = new ClassJSONParser();
                        String FinalURL = "http://api.plos.org/search?q=title:DNA";
                        jsonResponse = jParser.getJSONFromUrl(FinalURL);

                        // insert desired data into json object

                        // and return the json object
                        Log.i("***ConcurrencyPackage", jsonResponse.toString());
                        return jsonResponse;
                    }
                };

                future = executor.submit(callable);
                jsonResult = future.get();
            } catch (InterruptedException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            } catch (ExecutionException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            }

            return jsonResult;
        }
    }
}