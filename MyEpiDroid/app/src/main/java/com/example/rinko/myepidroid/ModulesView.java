package com.example.rinko.myepidroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.AdapterView.OnItemClickListener;

public class ModulesView extends ActionBarActivity {
    private String              Token = null;
    private String              UserSemester;
    private IntraManager        im = new IntraManager();
    private ListView            Modules;
    private String              ModulesGrade;
    final String                EXTRA_MODULE = "user_module";
    final String                EXTRA_CODEI = "user_codei";
    final String                EXTRA_CODEM = "user_codem";
    final String                EXTRA_TOKEN = "user_token";
    final String                EXTRA_STATUS = "user_status";
    final String                EXTRA_BEGIN = "user_begin";
    final String                EXTRA_END_REGISTER = "user_end_register";
    final String                EXTRA_SEMESTER = "user_semester";
    final String                EXTRA_GARDE = "user_grade";
    final String                EXTRA_CREDIT = "user_credit";
    private List<String>        allNames = new ArrayList<String>();
    public JSONArray            modules = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_view);
        setup_variable();

        Intent intent = getIntent();
        Token = intent.getStringExtra(EXTRA_TOKEN);
        UserSemester = intent.getStringExtra(EXTRA_SEMESTER);
        String ModulesResponse = im.allmodules(Token, MySingleton.getInstance().getScolaryear(), MySingleton.getInstance().getLocation(), MySingleton.getInstance().getCourseCode());
        ModulesGrade = im.modulesGrade(Token);

        JSONObject jUsersModules = null;
        try {
            jUsersModules = new JSONObject(ModulesResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            modules = jUsersModules.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0; i<modules.length(); i++) {
            JSONObject title = null;
            try {
                title = modules.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name = null;
            try {
                name = title.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserSemesterInfos = null;
            try {
                UserSemesterInfos = title.getString("semester");
                UserSemesterInfos = "B" + UserSemesterInfos;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UserSemesterInfos != null && UserSemesterInfos.equals("B0") || UserSemesterInfos.equals(UserSemester))
                allNames.add(name);
        }
        setup_modules_list();

    }

    private void setup_modules_list()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allNames);

        //On passe nos données au composant ListView
        Modules.setAdapter(adapter);
        Modules.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String module_title=(String)parent.getItemAtPosition(position);
                for (int i=0; i<modules.length(); i++) {
                    JSONObject module = null;
                    try {
                        module = modules.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name = null;
                    try {
                        name = module.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (name == module_title)
                    {
                        String codeInstance = null;
                        String codeModule = null;
                        String status = null;
                        String begin = null;
                        String end_register = null;
                        String grade = null;
                        String credit = null;

                        try {
                            codeInstance = module.getString("codeinstance");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            codeModule = module.getString("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            status = module.getString("status");
                            if (status.equals("fail"))
                                status = "Echec";
                            else if (status.equals("notreEchecgistered"))
                                status = "Pas inscrit";
                            else if (status.equals("valid"))
                                status = "Validé";
                            else if (status.equals("ongoing"))
                                status = "En cours";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            begin = module.getString("begin");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            end_register = module.getString("end_register");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject jUsersModulesGrades = null;
                        try {
                            jUsersModulesGrades = new JSONObject(ModulesGrade);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray grades = null;
                        try {
                            grades = jUsersModulesGrades.getJSONArray("modules");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int j=0; j<grades.length(); j++) {
                            JSONObject jGrade = null;
                            try {
                                jGrade = grades.getJSONObject(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String titleG = null;
                            try {
                                titleG = jGrade.getString("title");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (titleG.equals(name)) {
                                try {
                                    grade = jGrade.getString("grade");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            credit = module.getString("credits");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intentModulesView = new Intent(ModulesView.this, ModulesInscriptionView.class);
                        intentModulesView.putExtra(EXTRA_MODULE, module_title);
                        intentModulesView.putExtra(EXTRA_CODEI, codeInstance);
                        intentModulesView.putExtra(EXTRA_CODEM, codeModule);
                        intentModulesView.putExtra(EXTRA_STATUS, status);
                        intentModulesView.putExtra(EXTRA_BEGIN, begin);
                        intentModulesView.putExtra(EXTRA_END_REGISTER, end_register);
                        intentModulesView.putExtra(EXTRA_GARDE, grade);
                        intentModulesView.putExtra(EXTRA_CREDIT, credit);
                        startActivity(intentModulesView);

                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modules_view, menu);
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

    private void setup_variable()
    {
        this.Modules = (ListView)findViewById(R.id.ModulesList);
    }

}

