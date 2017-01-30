package com.example.rinko.myepidroid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProjectsInscriptionView extends ActionBarActivity {
    final String                EXTRA_PROJECT = "user_project";
    final String                EXTRA_CODEI = "user_codei";
    final String                EXTRA_CODEM = "user_codem";
    final String                EXTRA_CODEA = "user_codea";
    final String                EXTRA_REGISTERED = "user_registered";
    final String                EXTRA_MOD_TITLE = "user_mod_title";
    final String                EXTRA_NBMIN = "user_nbmin";
    final String                EXTRA_NBMAX = "user_nbmax";
    final String                EXTRA_BEGIN = "user_begin";
    final String                EXTRA_END = "user_end";
    final String                EXTRA_DEADLINE = "user_deadline";

    IntraManager                im = new IntraManager();
    private Button              inscrire, desinscrire;
    String projectTitle = null, codei = null, codem = null, codea = null, registered = null, mod_title = null;
    String nbMin = null, nbMax = null, begin = null, end = null, deadline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_inscription_view);
        Intent intent = getIntent();
        projectTitle = intent.getStringExtra(EXTRA_PROJECT);
        codei = intent.getStringExtra(EXTRA_CODEI);
        codem = intent.getStringExtra(EXTRA_CODEM);
        codea = intent.getStringExtra(EXTRA_CODEA);
        registered = intent.getStringExtra(EXTRA_REGISTERED);
        mod_title = intent.getStringExtra(EXTRA_MOD_TITLE);
        nbMin = intent.getStringExtra(EXTRA_NBMIN);
        nbMax = intent.getStringExtra(EXTRA_NBMAX);
        begin = intent.getStringExtra(EXTRA_BEGIN);
        end = intent.getStringExtra(EXTRA_END);
        deadline = intent.getStringExtra(EXTRA_DEADLINE);
        setup_variables();
    }

    void setup_variables(){
        TextView title = (TextView)findViewById(R.id.title);
        TextView module_name = (TextView)findViewById(R.id.module_name);
        TextView Begin = (TextView) findViewById(R.id.textViewBegin);
        TextView End = (TextView) findViewById(R.id.textViewEnd);
        inscrire = (Button)findViewById(R.id.inscrire);
        desinscrire = (Button)findViewById(R.id.desinscrire);

        inscrire.setClickable(true);
        inscrire.setTextColor(Color.BLACK);
        desinscrire.setClickable(true);
        desinscrire.setTextColor(Color.BLACK);

        title.setText(projectTitle);
        module_name.setText(mod_title);
        Begin.setText(begin);
        End.setText(end);
    }

    public void     Unsubscribe_onClick(View v) throws JSONException {
        String response = im.unsubproject(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), codem, codei, codea);
        Log.d("MyActivity", response);
        if (response.indexOf("error", 0) != -1) {
            JSONObject jError = null;
            try {
                jError = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Error = null;
            try {
                Error = jError.getString("error");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jMessage = null;
            try {
                jMessage = new JSONObject(Error);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Toast.makeText(getApplicationContext(), jMessage.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Vous êtes maintenant désincrit", Toast.LENGTH_SHORT).show();
        }
    }

    public void     Subscribe_onClick(View v) {
        String response = im.subproject(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), codem, codei, codea);
        Log.d("MyActivity", response);
        if (response.indexOf("error", 0) != -1) {
            JSONObject jError = null;
            try {
                jError = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Error = null;
            try {
                Error = jError.getString("error");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jMessage = null;
            try {
                jMessage = new JSONObject(Error);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jMessage != null) {
                try {
                    Toast.makeText(getApplicationContext(), jMessage.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Vous êtes maintenant inscrit", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projects_inscription_view, menu);
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
}
