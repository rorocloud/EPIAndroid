package com.example.rinko.myepidroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends ActionBarActivity {
    protected MyApplication app;
    private EditText login;
    private EditText passwd;
    private Button log_button;
    private TextView textView;
    private IntraManager im = new IntraManager();
    private String urldisplay;
    private Bitmap mIcon11;
    final String EXTRA_TOKEN = "user_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupVariables();
        app = (MyApplication)getApplication();
    }

    public void log_button_OnClick(View v) throws JSONException {
        String response = im.login(login.getText().toString(), passwd.getText().toString());
        if (response.indexOf("error", 0) != -1)
            Toast.makeText(getApplicationContext(), "Invalid passwd or login", Toast.LENGTH_SHORT).show();
        else {
            JSONObject jObject = new JSONObject(response);

            String aJsonString = jObject.getString("token");
            MySingleton.getInstance().setToken(aJsonString);
            Intent intent = new Intent(MainActivity.this, com.example.rinko.myepidroid.HomeView.class);
            intent.putExtra(EXTRA_TOKEN, aJsonString);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private void setupVariables() {
        login = (EditText) findViewById(R.id.login);
        passwd = (EditText) findViewById(R.id.passwd);
        log_button = (Button) findViewById(R.id.log_button);
    }

}
