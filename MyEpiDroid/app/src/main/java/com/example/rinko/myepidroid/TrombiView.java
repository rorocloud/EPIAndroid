package com.example.rinko.myepidroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class TrombiView extends ActionBarActivity {
    private IntraManager    im = new IntraManager();
    private String          urldisplay;
    private Bitmap          Photo;
    private List<String>    Names = new ArrayList<String>();
    private List<Bitmap>    Photos = new ArrayList<Bitmap>();
    private List<Trombi>    T = new ArrayList<Trombi>();
    private int             offset = 1;
    /*this.Photos.add(deddd);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 /*       Intent intent = getIntent();
        String Token;
        String Login;
        final String EXTRA_TOKEN = "user_token";
        final String EXTRA_LOGIN = "user_login";
        Token = intent.getStringExtra(EXTRA_TOKEN);
        Login = intent.getStringExtra(EXTRA_LOGIN);


         //test trombi
        String ResponseTrombi = null;
        JSONArray jTrombi = null;
        JSONObject jTemptrombi = null;


//        while( cpt < 10 && (ResponseTrombi = im.trombi(Token, Login, cpt)) != null) {
        ResponseTrombi = im.trombi(Token, Login, offset)
            try {
                jTemptrombi = new JSONObject(ResponseTrombi);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                jTrombi = new JSONArray(ResponseTrombi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert jTemptrombi != null;
            try {
                jTrombi = jTemptrombi.getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setContentView(R.layout.activity_trombi_view);
            JSONObject Temp;
            Trombi Trombi_temp = null;
            for (int i = 1; i < jTrombi.length(); i++) {
                try {
                    Temp = jTrombi.getJSONObject(i);
                    this.Names.add(Temp.getString("login"));
//                    this.Photos.add(get_img(Temp.getString("picture")));
                    Trombi_temp = new Trombi(get_img(Temp.getString("picture")), Temp.getString("login"));
                    this.T.add(Trombi_temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        //ici afficher la lists
        ListView Trombi_list;
        Trombi_list = (ListView)findViewById(R.id.Trombi_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Names);
        //Récupération du ListView présent dans notre IHM
        ImageView Test;
        Test = (ImageView)findViewById(R.id.Trombi_Photos);
        //On passe nos données au composant ListView
      Trombi_list.setAdapter(new  TrombiAdapter(getApplicationContext(), T));
        */
        Aff_page();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trombi_view, menu);
        return true;
    }

    private Bitmap get_img(final String url) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    java.net.URL test;
                    test = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) test.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Photo = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // return mIcon11;
            }
        });
        //     return (t.start());
        t.start();
        while (t.isAlive()){}
        return (Photo);
    }

    public void Aff_page()
    {
        Intent intent = getIntent();
        String Token;
        String Login;
        String Promo;
        String Location;
        final String EXTRA_TOKEN = "user_token";
        final String EXTRA_LOGIN = "user_login";
        final String EXTRA_PROMO = "user_promo";
        final String EXTRA_LOCATION = "user_location";
        Token = intent.getStringExtra(EXTRA_TOKEN);
        Login = intent.getStringExtra(EXTRA_LOGIN);
        Promo = intent.getStringExtra(EXTRA_PROMO);
        Location = intent.getStringExtra(EXTRA_LOCATION);


         /*test trombi*/
        String ResponseTrombi = null;
        JSONArray jTrombi = null;
        JSONObject jTemptrombi = null;


//        while( cpt < 10 && (ResponseTrombi = im.trombi(Token, Login, cpt)) != null) {
        ResponseTrombi = im.trombi(Token, Login, Promo, Location, offset);
        try {
            jTemptrombi = new JSONObject(ResponseTrombi);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jTrombi = new JSONArray(ResponseTrombi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jTemptrombi != null;
        try {
            jTrombi = jTemptrombi.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_trombi_view);
        JSONObject Temp;
        Trombi Trombi_temp = null;
        for (int i = 1; i < jTrombi.length(); i++) {
            try {
                Temp = jTrombi.getJSONObject(i);
                this.Names.add(Temp.getString("login"));
//                    this.Photos.add(get_img(Temp.getString("picture")));
                Trombi_temp = new Trombi(get_img(Temp.getString("picture")), Temp.getString("login"));
                this.T.add(Trombi_temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        /*ici afficher la lists*/
        ListView Trombi_list;
        Trombi_list = (ListView)findViewById(R.id.Trombi_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Names);
        //Récupération du ListView présent dans notre IHM
        ImageView Test;
        Test = (ImageView)findViewById(R.id.Trombi_Photos);
        //On passe nos données au composant ListView
        Trombi_list.setAdapter(new  TrombiAdapter(getApplicationContext(), T));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*    public void Next_button(View v)
    {
        offset = offset + 1;
        Log.d("MyActivity", String.valueOf(offset));
        Aff_page();
    }*/
}
