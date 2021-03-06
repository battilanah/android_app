package com.example.hugob.apphugo;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.*;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    static String URL_API = "http://voyage2.corellis.eu/api/v2/homev2?lat=43.14554197717751&lon=6.00.246207789145&offset=0";

    public String result = "RIEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Access the RequestQueue through your singleton class.

        this.gatherData(URL_API);
        setContentView(R.layout.activity_main);
    }


    public void traitementData(JSONObject result){
        Log.v("JSON", result.toString());

        Intent activite_list = new Intent(getApplicationContext(), SecondActivity.class);
        //On passe les données à la prochaine activité
        activite_list.putExtra("json", result.toString());
        //Puis on démarre l'activité
        startActivity(activite_list);

        finish();
    }


    public void gatherData(String url){
        RequestQueue mRequestQueue;
        // Instantiate the run
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MainActivity.this.result = response.toString();
                        MainActivity.this.traitementData(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");


                              List<String> list= new ArrayList<>() ;


                            for (int i = 0; i < jsonArray.length(); i++) {
                                String a= jsonArray.getJSONObject(i).getString("id");
                                Log.d("var", a); //4th commit retrieve one component
                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    //afficher dans les logcats
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }

                });
        mRequestQueue.add(jsonObjectRequest);
    }


}




