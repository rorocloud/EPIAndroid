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


public class ModulesInscriptionView extends ActionBarActivity {
    final String            EXTRA_MODULE = "user_module";
    final String            EXTRA_CODEI = "user_codei";
    final String            EXTRA_CODEM = "user_codem";
    final String            EXTRA_STATUS = "user_status";
    final String            EXTRA_BEGIN = "user_begin";
    final String            EXTRA_END_REGISTER = "user_end_register";
    final String            EXTRA_GARDE = "user_grade";
    final String            EXTRA_CREDIT = "user_credit";

    IntraManager            im = new IntraManager();
    private Button          inscrire, desinscrire;
    private TextView        text, Status, Begin, End_register, Grade, Credit;
    private String          codem;
    private String          codei;
    private String          status;
    private String          begin;
    private String          end_register;
    private String          moduleTitle;
    private String          gr;
    private String          cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_inscription_view);
        Intent intent = getIntent();
        moduleTitle = intent.getStringExtra(EXTRA_MODULE);
        codem = intent.getStringExtra(EXTRA_CODEM);
        codei = intent.getStringExtra(EXTRA_CODEI);
        status = intent.getStringExtra(EXTRA_STATUS);
        begin = intent.getStringExtra(EXTRA_BEGIN);
        end_register = intent.getStringExtra(EXTRA_END_REGISTER);
        gr = intent.getStringExtra(EXTRA_GARDE);
        cr = intent.getStringExtra(EXTRA_CREDIT);
        setup_variable();
    }

    void setup_variable()
    {
        text = (TextView)findViewById(R.id.title);
        Status = (TextView)findViewById(R.id.status);
        Begin = (TextView)findViewById(R.id.start_time);
        End_register = (TextView)findViewById(R.id.end_time);
        Grade = (TextView) findViewById(R.id.grade);
        Credit = (TextView) findViewById(R.id.credit);
        inscrire = (Button)findViewById(R.id.inscrire);
        desinscrire = (Button)findViewById(R.id.desinscrire);
        if (status.equals("Validé") || status.equals("Echec")) {
            inscrire.setClickable(false);
            inscrire.setTextColor(Color.GRAY);
            desinscrire.setClickable(false);
            desinscrire.setTextColor(Color.GRAY);
        } else if (status.equals("En cours") || status.equals("Pas inscrit")) {
            inscrire.setClickable(true);
            inscrire.setTextColor(Color.BLACK);
            desinscrire.setClickable(true);
            desinscrire.setTextColor(Color.BLACK);
        }
        text.setText(moduleTitle);
        Status.setText(status);
        Begin.setText(begin);
        End_register.setText(end_register);
        if (gr == null)
            gr = "Aucun";
        Grade.setText("Grade : " + gr);
        Credit.setText("Crédit : " + cr);
    }

    public void     Unsubscribe_onClick(View v) {
        String response = im.unsubmodule(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), codem, codei);
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
            Toast.makeText(getApplicationContext(), "Vous êtes maintenant désincrit", Toast.LENGTH_SHORT).show();
        }
    }

    public void     Subscribe_onClick(View v) {
        String response = im.submodule(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), codem, codei);
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
        getMenuInflater().inflate(R.menu.menu_modules_inscription_view, menu);
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
