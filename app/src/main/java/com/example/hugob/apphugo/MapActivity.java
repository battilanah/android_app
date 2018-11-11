package com.example.hugob.apphugo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import java.util.ArrayList;

public class MapActivity extends Activity {
    private GoogleMap map;
    private GoogleMap maps;
    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> mapp;
    //  FacilitiesAdapter fadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mapp = new HashMap<String, String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        setTitle("Nearby Facilities");
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        LatLng HFLoc = new LatLng(-1.8691541, 30.0058391);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HFLoc, 8));
        map.addMarker(new MarkerOptions()
                .title("Here")
                .snippet("Here")
                .position(HFLoc));

        new JSONAsyncTask().execute("http://voyage2.corellis.eu/api/v2/homev2?lat=431554197717751&lon=6.00246207789145&offset=0");

    }
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MapActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting to server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                if(getIntent().hasExtra("json")) {

                        JSONObject jobject = new JSONObject(getIntent().getStringExtra("json"));


                        JSONArray jarray = jobject.getJSONArray("data");


                        for (int i=0; i<jarray.length(); i++){
                        JSONObject object = jarray.getJSONObject( i );


                        double lat = object.getDouble( "lat" );
                        double lon = object.getDouble( "lon" );
                        System.out.println( "Lat: " + lat + " Lon: " + lon );

                                //use a for loop to process your json data here
                                map.addMarker( new MarkerOptions()
                                        .position( new LatLng( lat, lon ) )
                                        .title( "Title for marker" ) );
                    }




                    }
                    return true;
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //------------------>>  b

            return false;
        }
        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            //fadapter.notifyDataSetChanged();
            try{
                for(int i = 0; i< listItem.size(); i++){
                    System.out.println("Latitude: " + listItem.get(i));
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                System.out.println("This went wrong: " + e.getMessage());
            }
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();



}

    }




}