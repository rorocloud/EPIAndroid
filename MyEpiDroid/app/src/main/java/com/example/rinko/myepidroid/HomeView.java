package com.example.rinko.myepidroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rinko.myepidroid.IntraManager;
import com.example.rinko.myepidroid.ModulesView;
import com.example.rinko.myepidroid.PlanningView;
import com.example.rinko.myepidroid.ProjectsView;
import com.example.rinko.myepidroid.R;
import com.example.rinko.myepidroid.TrombiView;

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
import java.util.Objects;

import static java.util.Objects.requireNonNull;


public class HomeView extends ActionBarActivity {
//    protected MyApplication app;
    private String          Token;
    private String          InfosResponseStr;
    private String          UserResponseStr;
    private String          Promo;
    private String          Location;
    private TextView        Login;
    private TextView        Nom;
    private TextView        Ville;
    private TextView        Promotion;
    private TextView        Semestre;
    private TextView        UserSemester;
    private TextView        Credit;
    private TextView        Spices;
    private TextView        Netsoul;
    private TextView        Alert;
    private IntraManager    im = new IntraManager();
    private String          urldisplay;
    private Bitmap          mIcon11;
    final String            EXTRA_TOKEN = "user_token";
    final String            EXTRA_DATE = "date_current";
    final String            EXTRA_BWEEK = "begin_week";
    final String            EXTRA_EWEEK = "end_week";
    private String up_alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        //app = (MyApplication)getApplication();
        setup_variables();
        Intent intent = getIntent();
        Token = intent.getStringExtra(EXTRA_TOKEN);
        InfosResponseStr = im.infos(Token);
        String res = im.messages(Token);
        setup_messages();
        try {
            setup_messages2(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String alert = im.alert(Token);
        setup_alert(alert);
        setup_usersinfos();
    }

    public void setup_messages2(String res) throws JSONException {
        TextView title1 = (TextView)findViewById(R.id.title_textView);
        TextView title2 = (TextView)findViewById(R.id.title2_textView);
        TextView title3 = (TextView)findViewById(R.id.title3_textView);
        TextView content1 = (TextView)findViewById(R.id.content_textView);
        TextView content2 = (TextView)findViewById(R.id.content2_textView);
        TextView content3 = (TextView)findViewById(R.id.content3_textView);
        TextView date1 = (TextView)findViewById(R.id.date_textView);
        TextView date2 = (TextView)findViewById(R.id.date2_textView);
        TextView date3 = (TextView)findViewById(R.id.date3_textView);
        JSONArray MessagesArray = null;
        try {
            MessagesArray = new JSONArray(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("MyActivity", "Yololololohuhu" + res);

        title1.setText(Html.fromHtml(MessagesArray.getJSONObject(1).getString("title")));
        title2.setText(Html.fromHtml(MessagesArray.getJSONObject(2).getString("title")));
        title3.setText(Html.fromHtml(MessagesArray.getJSONObject(3).getString("title")));
        content1.setText(Html.fromHtml(MessagesArray.getJSONObject(1).getString("content")));
        content2.setText(Html.fromHtml(MessagesArray.getJSONObject(2).getString("content")));
        content3.setText(Html.fromHtml(MessagesArray.getJSONObject(3).getString("content")));
        date1.setText(Html.fromHtml(MessagesArray.getJSONObject(1).getString("date")));
        date2.setText(Html.fromHtml(MessagesArray.getJSONObject(2).getString("date")));
        date3.setText(Html.fromHtml(MessagesArray.getJSONObject(3).getString("date")));
    }

    private void setup_messages()
    {
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.message_container);
        View view = getLayoutInflater().inflate(R.layout.activity_messages_view, mainLayout,false);
        mainLayout.addView(view);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setup_usersinfos()
    {
        JSONObject jInfos = null;
        try {
            jInfos = new JSONObject(InfosResponseStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("MyActivity", "<== INFOS ==> " + InfosResponseStr);
        String UserInfosInfos = null;
        try {
            UserInfosInfos = jInfos.getString("infos");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String UserInfosCurrent = null;
        try {
            UserInfosCurrent = jInfos.getString("current");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jInfosInfos = null;
        try {
            jInfosInfos = new JSONObject(UserInfosInfos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jInfosCurrent = null;
        try {
            jInfosCurrent = new JSONObject(UserInfosCurrent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            MySingleton.getInstance().setCourseCode(jInfosInfos.getString("course_code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            MySingleton.getInstance().setLogin(jInfosInfos.getString("login"));
            Login.setText(MySingleton.getInstance().getLogin());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Location = jInfosInfos.getString("location").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Nom.setText(jInfosInfos.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            MySingleton.getInstance().setLocation(jInfosInfos.getString("location"));
            Ville.setText(MySingleton.getInstance().getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Promo = jInfosInfos.getString("promo").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Promotion.setText("Promotion " + jInfosInfos.getString("promo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            MySingleton.getInstance().setSemester(jInfosInfos.getString("semester"));
            UserSemester.setText(MySingleton.getInstance().getSemester());
            Semestre.setText("B" + MySingleton.getInstance().getSemester());
        } catch (JSONException e) {
            e.printStackTrace();
        }
/*on ajoute la photo*/
        String ResponsePhoto = null;
        try {
            ResponsePhoto = im.photo(Token, jInfosInfos.getString("login"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jPhoto = null;
        try {
            jPhoto = new JSONObject(ResponsePhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            urldisplay = jPhoto.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("MyActivity", "URL PHOTO ==" + urldisplay);
        mIcon11 = null;
        get_img();
        ImageView my = (ImageView) findViewById(R.id.my_img);
        my.setImageBitmap(mIcon11);
        UserResponseStr = im.user(Token, Login.getText().toString());
        Log.d("MyActivity", "<== USER ==> " + UserResponseStr);
        JSONObject jUser = null;
        try {
            jUser = new JSONObject(UserResponseStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String netsoul = null;
        try {
            netsoul = jUser.getString("nsstat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jNetsoul = null;
        try {
            jNetsoul = new JSONObject(netsoul);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Netsoul.setText("Netsoul : " + jNetsoul.getString("active") + "h");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String spice = null;
        try {
            spice = jUser.getString("spice");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jSpice = null;
        try {
            jSpice = new JSONObject(spice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Spice = null;
        try {
            Spice = jSpice.getString("available_spice");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int spiceCredit = Integer.parseInt(Spice) / 60;
        if (spiceCredit != 0) {
            Spices.setText("Vous pouvez acquerir " + Spice.valueOf(spiceCredit) + " crédits avec vos épices.");
        }
        try {
            Credit.setText("Vous avez " + jUser.getString("credits") + " crédits.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setup_variables()
    {
        Login = (TextView) findViewById(R.id.Login_textView);
        Nom = (TextView) findViewById(R.id.Nom_textView);
        Ville = (TextView) findViewById(R.id.Ville_textView);
        Promotion = (TextView) findViewById(R.id.Promotion_textView);
        UserSemester = (TextView) findViewById(R.id.Semestre_textView);
        Semestre = (TextView) findViewById(R.id.Semestre_textView);
        Credit = (TextView) findViewById(R.id.Credit_textView);
        Spices = (TextView) findViewById(R.id.Spice_textView);
        Netsoul = (TextView) findViewById(R.id.Netsoul_textView);
        Alert = (TextView) findViewById(R.id.Alert_textView);
    }

    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PROMO = "user_promo";
    final String EXTRA_LOCATION = "user_location";
    final String EXTRA_SEMESTER = "user_semester";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_view, menu);
        return true;
    }

    public void ModulesButton_OnClick(View v) {
        Intent intentModulesView = new Intent(HomeView.this, ModulesView.class);
        intentModulesView.putExtra(EXTRA_TOKEN, Token);
        intentModulesView.putExtra(EXTRA_SEMESTER, UserSemester.getText().toString());
        startActivity(intentModulesView);
    }
    public void ProjetsButton_OnClick(View v) {
        Intent intentProjectsView = new Intent(HomeView.this, ProjectsView.class);
        startActivity(intentProjectsView);
    }
    public void TrombiButton_OnClick(View v) {
        Intent intentTrombiView = new Intent(HomeView.this, TrombiView.class);
        intentTrombiView.putExtra(EXTRA_TOKEN, Token);
        intentTrombiView.putExtra(EXTRA_LOGIN, Login.toString());
        intentTrombiView.putExtra(EXTRA_PROMO, Promo.toString());
        intentTrombiView.putExtra(EXTRA_LOCATION, Location.toString());

        startActivity(intentTrombiView);
    }
    public void PlanningButton_OnClick(View v) {
        Intent intentPlanningView = new Intent(HomeView.this, PlanningView.class);
        intentPlanningView.putExtra(EXTRA_TOKEN, Token);
        intentPlanningView.putExtra(EXTRA_SEMESTER, UserSemester.getText().toString());
        intentPlanningView.putExtra(EXTRA_DATE, "null");
        intentPlanningView.putExtra(EXTRA_BWEEK, "null");
        intentPlanningView.putExtra(EXTRA_EWEEK, "null");
        startActivity(intentPlanningView);
    }


    private void get_img() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    java.net.URL test;
                    test = new URL(urldisplay);
                    HttpURLConnection connection = (HttpURLConnection) test.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    mIcon11 = BitmapFactory.decodeStream(inputStream);
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

    public void setup_alert(String alert) {
        JSONArray AlertArray = null;
        try {
            AlertArray = new JSONArray(alert);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AlertArray != null) {
            for (int i = 0; i < AlertArray.length(); i++) {
                JSONObject jAlert = null;
                try {
                    jAlert = AlertArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Alert.setText(jAlert.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
