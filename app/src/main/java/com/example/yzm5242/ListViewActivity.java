package com.example.yzm5242;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListViewActivity extends AppCompatActivity {
    List<HashMap<String, String>> list;
    SimpleAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lv = findViewById(R.id.lv);


        //ListView with simple_list_item_1
        /*String items[]={"Manisa", "İzmir", "Istanbul","Ankara","Antalya","Bursa"};

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String value = adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

            }
        });*/



        //ListView with simple_list_item_2
        /*String[] fromMapKey = {"key1", "key2"};
        int[] toLayoutId = new int[]{android.R.id.text1, android.R.id.text2};

        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap hm = new HashMap();
        hm.put("key1", "Manisa");
        hm.put("key2", "45");
        list.add(hm);

        hm = new HashMap();
        hm.put("key1", "İzmir");
        hm.put("key2", "35");
        list.add(hm);

        hm = new HashMap();
        hm.put("key1", "İstanbul");
        hm.put("key2", "34");
        list.add(hm);

        hm = new HashMap();
        hm.put("key1", "Ankara");
        hm.put("key2", "06");
        list.add(hm);

        hm = new HashMap();
        hm.put("key1", "Bursa");
        hm.put("key2", "16");
        list.add(hm);

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                HashMap hm = list.get(position);
                String value = hm.get("key2").toString();
                Log.i("***value",value);
                Toast.makeText(ListViewActivity.this,value, Toast.LENGTH_SHORT).show();

            }
        });*/


        //ListView with custom_listview_row
        /*String[] fromMapKey = {"il_adi", "plaka_kodu","tel_kodu"};
        int[] toLayoutId = new int[]{R.id.tv1, R.id.tv2, R.id.tv3};

        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap hm = new HashMap();
        hm.put("il_adi", "Manisa");
        hm.put("plaka_kodu", "45");
        hm.put("tel_kodu", "236");
        list.add(hm);

        hm = new HashMap();
        hm.put("il_adi", "İzmir");
        hm.put("plaka_kodu", "35");
        hm.put("tel_kodu", "232");
        list.add(hm);

        hm = new HashMap();
        hm.put("il_adi", "İstanbul");
        hm.put("plaka_kodu", "34");
        hm.put("tel_kodu", "212-216");
        list.add(hm);

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.custom_listview_row, fromMapKey, toLayoutId);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                HashMap hm = list.get(position);
                String value = hm.get("tel_kodu").toString();
                Log.i("***value",value);
                Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();

            }
        });*/

        //ListView with custom_listview_row from JSON data
        String[] fromMapKey = {"key1", "key2","key3"};
        int[] toLayoutId = new int[]{R.id.tv1, R.id.tv2, R.id.tv3};
        list = new ArrayList<>();
        adapter = new SimpleAdapter(this, list, R.layout.custom_listview_row, fromMapKey, toLayoutId);
        lv.setAdapter(adapter);
        if (isOnline()) {
            Test t = new Test();
            t.doTheWork();
        }
    }


    public class Test {
        ExecutorService executor;

        public Test() {
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
                        JSONObject jsonResult = new JSONObject();

                        ClassJSONParser jParser = new ClassJSONParser();
                        String FinalURL = "http://api.plos.org/search?q=title:DNA";
                        jsonResult = jParser.getJSONFromUrl(FinalURL);


                        Log.i("***TestMetodu", jsonResult.toString());

                        //((SimpleAdapter)lv.getAdapter()).notifyDataSetChanged();

                        return jsonResult;
                    }
                };

                future = executor.submit(callable);
                jsonResult = future.get();
                JSONObject jo = jsonResult.getJSONObject("response");
                JSONArray joArray = jo.getJSONArray("docs");
                for(int i = 0; i < joArray.length();i++){
                    JSONObject object = joArray.getJSONObject(i);

                    HashMap hm = new HashMap();
                    hm.put("key1", object.getString("title_display"));
                    hm.put("key2", object.getString("journal"));
                    hm.put("key3", object.getString("eissn"));
                    list.add(hm);
                }
            } catch (InterruptedException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            } catch (ExecutionException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonResult;
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
}