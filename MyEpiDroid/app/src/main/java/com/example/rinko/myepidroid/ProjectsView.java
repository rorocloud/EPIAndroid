package com.example.rinko.myepidroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProjectsView extends ActionBarActivity {
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
    private ListView            Projects;
    private List<String>        allNames = new ArrayList<String>();
    JSONArray                   projects = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_view);
        setup_variable();

        String ProjectsResponse = im.projects(MySingleton.getInstance().getToken());

        try {
            projects = new JSONArray(ProjectsResponse);

        } catch (JSONException e) {
            e.printStackTrace();
        }


       for (int i=0; i<projects.length(); i++) {
            JSONObject project = null;
            try {
                project = projects.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name = null;
            try {
                name = project.getString("acti_title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
           try {
               String p = project.getString("type_acti");
//               if (p.equals("Projet") || p.equals("Projets"))
                   allNames.add(name);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
        setup_modules_list();

    }

    private void setup_modules_list()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allNames);

        //On passe nos données au composant ListView
        Projects.setAdapter(adapter);
        Projects.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String project_title=(String)parent.getItemAtPosition(position);
                for (int i=0; i<projects.length(); i++) {
                    JSONObject project = null;
                    try {
                        project = projects.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("P: " + projects.getString(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name = null;
                    try {
                        name = project.getString("acti_title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (name == project_title)
                    {
                        String codeInstance = null;
                        String codeModule = null;
                        String codeacti = null;
                        String registered = null;
                        String mod_title = null;
                        String nbMin = null;
                        String nbMax = null;
                        String begin = null;
                        String end = null;
                        String deadLine = null;
                        try {
                            codeInstance = project.getString("codeinstance");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            codeModule = project.getString("codemodule");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            codeacti = project.getString("codeacti");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            registered = project.getString("registered");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            mod_title = project.getString("title_module");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            nbMin = project.getString("nb_min");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            nbMax = project.getString("nb_max");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            begin = project.getString("begin_acti");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            end = project.getString("end_acti");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            deadLine = project.getString("deadline");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(nbMin);
                        System.out.println(nbMax);
                        System.out.println(begin);
                        System.out.println(end);
                        System.out.println(deadLine);
                        Intent intentProjectsView = new Intent(ProjectsView.this, ProjectsInscriptionView.class);
                        intentProjectsView.putExtra(EXTRA_PROJECT, project_title);
                        intentProjectsView.putExtra(EXTRA_CODEI, codeInstance);
                        intentProjectsView.putExtra(EXTRA_CODEM, codeModule);
                        intentProjectsView.putExtra(EXTRA_CODEA, codeacti);
                        intentProjectsView.putExtra(EXTRA_REGISTERED, registered);
                        intentProjectsView.putExtra(EXTRA_MOD_TITLE, mod_title);
                        intentProjectsView.putExtra(EXTRA_NBMIN, nbMin);
                        intentProjectsView.putExtra(EXTRA_NBMAX, nbMax);
                        intentProjectsView.putExtra(EXTRA_BEGIN, begin);
                        intentProjectsView.putExtra(EXTRA_END, end);
                        intentProjectsView.putExtra(EXTRA_DEADLINE, deadLine);
                        startActivity(intentProjectsView);
                    }
                }
            }
        });
    }

    private void setup_variable()
    {
        Projects = (ListView)findViewById(R.id.ProjectsList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projects_view, menu);
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
