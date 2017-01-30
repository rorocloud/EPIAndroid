package com.example.rinko.myepidroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;


public class PlanningInscriptionView extends ActionBarActivity {
    final String            EXTRA_MODULE = "user_module";
    final String            EXTRA_ACTIVITY = "user_activity";
    final String            EXTRA_PRESENT = "user_present";
    final String            EXTRA_BEGIN = "user_begin";
    final String            EXTRA_END = "user_end";
    final String            EXTRA_ROOM = "user_room";
    final String            EXTRA_SEATS = "user_seats";
    final String            EXTRA_PROF = "user_prof";
    final String            EXTRA_REGISTER = "user_register";
    final String            EXTRA_ALLOW = "user_allow";
    final String            EXTRA_CODEM = "code_module";
    final String            EXTRA_CODEI = "code_instance";
    final String            EXTRA_CODEA = "code_activity";
    final String            EXTRA_CODEE = "code_event";

    IntraManager            im = new IntraManager();

    private TextView        Activity;
    private TextView        ModuleName;
    private TextView        Begin;
    private TextView        End;
    private TextView        Seats;
    private TextView        Teacher;
    private TextView        Room;
    private EditText        MyToken;

    private Button          inscrire;
    private Button          desinscrire;
    private Button          validation;
    private String          status;
    private String          CodeM;
    private String          CodeI;
    private String          CodeA;
    private String          CodeE;
    private String          Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_inscription_view);
        Intent intent = getIntent();
        status = intent.getStringExtra(EXTRA_ALLOW);
        CodeM = intent.getStringExtra(EXTRA_CODEM);
        CodeI = intent.getStringExtra(EXTRA_CODEI);
        CodeA = intent.getStringExtra(EXTRA_CODEA);
        CodeE = intent.getStringExtra(EXTRA_CODEE);
        Register = intent.getStringExtra(EXTRA_REGISTER);

        setup_variable();
        Activity.setText(intent.getStringExtra(EXTRA_ACTIVITY));
        ModuleName.setText(intent.getStringExtra(EXTRA_MODULE));
        Begin.setText(intent.getStringExtra(EXTRA_BEGIN));
        End.setText(intent.getStringExtra(EXTRA_END));
        Seats.setText(intent.getStringExtra(EXTRA_SEATS));
        Teacher.setText(intent.getStringExtra(EXTRA_PROF));
        Room.setText(intent.getStringExtra(EXTRA_ROOM));
    }

    void setup_variable()
    {
        Activity = (TextView)findViewById(R.id.activityName);
        ModuleName = (TextView)findViewById(R.id.moduleName);
        Begin = (TextView)findViewById(R.id.start_time);
        End = (TextView)findViewById(R.id.end_time);
        Seats = (TextView) findViewById(R.id.places);
        Room = (TextView) findViewById(R.id.salle);
        Teacher = (TextView) findViewById(R.id.intervenant);
        inscrire = (Button)findViewById(R.id.inscrire);
        desinscrire = (Button)findViewById(R.id.desinscrire);
        validation = (Button)findViewById(R.id.validateToken);
        MyToken = (EditText) findViewById(R.id.editTextToken);
        if (status.equals("false")) {
            inscrire.setClickable(false);
            inscrire.setTextColor(Color.GRAY);
            desinscrire.setClickable(false);
            desinscrire.setTextColor(Color.GRAY);
        } else if (status.equals("true")) {
            inscrire.setClickable(true);
            inscrire.setTextColor(Color.BLACK);
            desinscrire.setClickable(true);
            desinscrire.setTextColor(Color.BLACK);
        }
    }

    public void     Unsubscribe_onClick(View v) {
        String response = im.unsubevent(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), CodeM, CodeI, CodeA, CodeE);
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

    public void Subscribe_onClick(View v) {
        String response = im.subevent(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), CodeM, CodeI, CodeA, CodeE);
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

    public void     Validate_onClick(View v) {
        String response = im.validateToken(MySingleton.getInstance().getToken(), MySingleton.getInstance().getScolaryear(), CodeM, CodeI, CodeA, CodeE, MyToken.getText().toString());
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
            Toast.makeText(getApplicationContext(), "Token Valide", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning_inscription_view, menu);
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
