package com.example.hugob.apphugo;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class SecondActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        map = new HashMap<String, String>();


        if(getIntent().hasExtra("json")) {
            try {
                JSONObject donnees_poi = new JSONObject(getIntent().getStringExtra("json"));


                JSONArray tableau_poi = donnees_poi.getJSONArray("data");

                for (int i=0; i < tableau_poi.length(); i++) {


                    if(tableau_poi.getJSONObject(i).get("type").toString().equals("POI")) {
                        map = new HashMap<String, String>();
                        map.put("nom", tableau_poi.getJSONObject(i).get("display").toString());
                        map.put("ville", tableau_poi.getJSONObject(i).get("city").toString());
                        map.put("etoiles", tableau_poi.getJSONObject(i).get("stars").toString());



                        map.put("img", tableau_poi.getJSONObject(i).get("media").toString());
                        listItem.add(map);
                    }
                }
            } catch (Throwable t) {
                Log.e("Erreur","Erreur");
            }
        }

        NewAdapter mSchedule = new NewAdapter (this.getBaseContext(), listItem, R.layout.activity_second,
                new String[] {"nom", "ville", "etoiles", "img"}, new int[] {R.id.nom, R.id.ville, R.id.etoiles, R.id.img});

        setListAdapter((ListAdapter) mSchedule);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) getListAdapter().getItem(position);
        Toast.makeText(this, map.get("nom")+ " selected", Toast.LENGTH_LONG).show();
    }

}