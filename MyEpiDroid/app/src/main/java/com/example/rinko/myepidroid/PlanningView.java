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
import android.widget.TextView;

import java.text.ParseException;
import java.util.GregorianCalendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

public class PlanningView extends ActionBarActivity {
    private String          ResponsePlanning;
    private String          UserSemester;
    private String          Token;
    private String          Start;
    private String          End;
    private static int      nb_week = 0;
    private Calendar        StartWeek;
    private Calendar        EndWeek;
    private IntraManager    im = new IntraManager();
    private ListView        Planning;
    private Date            DateCurrent;
    private TextView        Day;
    final String            EXTRA_TOKEN = "user_token";
    final String            EXTRA_SEMESTER = "user_semester";
    final String            EXTRA_DATE = "date_current";
    final String            EXTRA_MODULE = "user_module";
    final String            EXTRA_ACTIVITY = "user_activity";
    final String            EXTRA_BEGIN = "user_begin";
    final String            EXTRA_END = "user_end";
    final String            EXTRA_SEATS = "user_seats";
    final String            EXTRA_ROOM = "user_room";
    final String            EXTRA_PROF = "user_prof";
    final String            EXTRA_PRESENT = "user_present";
    final String            EXTRA_REGISTER = "user_register";
    final String            EXTRA_ALLOW = "user_allow";
    final String            EXTRA_BWEEK = "begin_week";
    final String            EXTRA_EWEEK = "end_week";
    final String            EXTRA_CODEM = "code_module";
    final String            EXTRA_CODEI = "code_instance";
    final String            EXTRA_CODEA = "code_activity";
    final String            EXTRA_CODEE = "code_event";

    private List<String>    allNames = new ArrayList<String>();
    public JSONArray        jPlanning = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_view);
        setup_variable();

        Intent intent = getIntent();

        UserSemester = intent.getStringExtra(EXTRA_SEMESTER);
        String tmp = intent.getStringExtra(EXTRA_DATE);
        if (tmp.equals("null")) {
            DateCurrent = (new GregorianCalendar()).getTime();
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                DateCurrent = formatter.parse(tmp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Token = intent.getStringExtra(EXTRA_TOKEN);

        // Date du jour
        Calendar TodayDate = Calendar.getInstance();

        // Initialise le début de semaine sur le Lundi
        TodayDate.setFirstDayOfWeek(Calendar.MONDAY);
        TodayDate.setTime(DateCurrent);

        // Date début de semaine
        String start_week = intent.getStringExtra(EXTRA_BWEEK);
        if (start_week.equals("null")) {
            StartWeek = (Calendar) TodayDate.clone();
            StartWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                StartWeek.setTime(formatter.parse(start_week));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Date fin de semaine
        String end_week = intent.getStringExtra(EXTRA_BWEEK);
        if (end_week.equals("null")) {
            EndWeek = (Calendar) TodayDate.clone();
            EndWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                EndWeek.setTime(formatter.parse(end_week));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (nb_week < 0) {
            StartWeek.add(Calendar.WEEK_OF_MONTH, -1 + 1);
            EndWeek.add(Calendar.WEEK_OF_MONTH, -1 + 1);
        } else if (nb_week > 0) {
            StartWeek.add(Calendar.WEEK_OF_MONTH, 1 - 1);
            EndWeek.add(Calendar.WEEK_OF_MONTH, 1 - 1);
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Start = sdf.format(StartWeek.getTime());
        End = sdf.format(EndWeek.getTime());
        ResponsePlanning = null;

        ResponsePlanning = im.Planning(Token, Start, End);

        Log.d("MyActivity", ResponsePlanning);

        setup_planninginfos();
    }

    private void setup_planninginfos() {
        jPlanning = null;
        try {
            jPlanning = new JSONArray(ResponsePlanning);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        String day = null;
        String month = null;
        for (int i = 0; i < jPlanning.length(); i++) {
            JSONObject Planning = null;
            try {
                Planning = jPlanning.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserStartInfos = null;
            try {
                assert jPlanning != null;
                UserStartInfos = Planning.getString("start");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserEndInfos = null;
            try {
                UserEndInfos = Planning.getString("end");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserActivityInfos = null;
            try {
                UserActivityInfos = Planning.getString("acti_title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserSemesterInfos = null;
            try {
                UserSemesterInfos = Planning.getString("semester");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String UserModuleRegisteredInfos = null;
            try {
                UserModuleRegisteredInfos = Planning.getString("module_registered");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            UserSemesterInfos = "B" + UserSemesterInfos;
            if ((UserSemesterInfos != null && UserModuleRegisteredInfos != null)&& (UserSemesterInfos.equals(UserSemester) || UserSemesterInfos.equals("B0")) && UserModuleRegisteredInfos.equals("true")) {
                String date = null;
                day = new SimpleDateFormat("EEEE").format(DateCurrent);
                month = new SimpleDateFormat("MMMM").format(DateCurrent);
                Day = (TextView) findViewById(R.id.Day);
                date = new SimpleDateFormat("yyyy-MM-dd").format(DateCurrent);
                Day.setText(day + " " + date.substring(date.lastIndexOf('-') + 1) + " " + month);
                if (date.substring(date.lastIndexOf('-') + 1).equals(UserStartInfos.substring(UserStartInfos.lastIndexOf(' ') - 2, UserStartInfos.lastIndexOf(' ')))) {
                    allNames.add(UserActivityInfos + "\n" + UserStartInfos.substring(UserStartInfos.lastIndexOf(':') - 5, UserStartInfos.lastIndexOf(':')) + " - " + UserEndInfos.substring(UserEndInfos.lastIndexOf(':') - 5, UserEndInfos.lastIndexOf(':')));
                }
            }
        }
        setup_planning_list();
    }

    private void setup_planning_list()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allNames);
        Planning.setAdapter(adapter);
        Planning.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String planning_title = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < jPlanning.length(); i++) {
                    JSONObject jplanning = null;
                    try {
                        jplanning = jPlanning.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String title = null;
                    try {
                        title = jplanning.getString("acti_title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String start = null;
                    try {
                        start = jplanning.getString("start");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String end = null;
                    try {
                        end = jplanning.getString("end");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (planning_title.equals(title + "\n" + start.substring(start.lastIndexOf(':') - 5, start.lastIndexOf(':')) + " - " + end.substring(end.lastIndexOf(':') - 5, end.lastIndexOf(':')))) {
                        String UserTokenInfos = null;
                        try {
                            UserTokenInfos = jplanning.getString("allow_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String UserAllowRegisterInfos = null;
                        try {
                            UserAllowRegisterInfos = jplanning.getString("allow_register");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String UserRegisterStudentInfos = null;
                        try {
                            UserRegisterStudentInfos = jplanning.getString("register_student");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String UserModuleInfos = null;
                        try {
                            UserModuleInfos = jplanning.getString("titlemodule");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String CodeModule = null;
                        try {
                            CodeModule = jplanning.getString("codemodule");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String CodeInstance = null;
                        try {
                            CodeInstance = jplanning.getString("codeinstance");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String CodeActivity = null;
                        try {
                            CodeActivity = jplanning.getString("codeacti");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String CodeEvent = null;
                        try {
                            CodeEvent = jplanning.getString("codeevent");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String UserTotalRegisterInfos = null;
                        try {
                            UserTotalRegisterInfos = jplanning.getString("total_students_registered");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String RoomInfos = null;
                        try {
                            RoomInfos = jplanning.getString("room");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String Room = null;
                        String Seats = null;
                        if (RoomInfos != null) {
                            JSONObject jRoom = null;
                            try {
                                jRoom = new JSONObject(RoomInfos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String SeatsMax = jRoom.getString("seats");
                                Seats = UserTotalRegisterInfos + "/" + SeatsMax;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String tmp = jRoom.getString("code");
                                Room = tmp.substring(tmp.lastIndexOf('/') + 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        JSONArray TeacherInfos = null;
                        try {
                            TeacherInfos = new JSONArray(jplanning.getString("prof_inst"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String Teacher = null;
                        if (TeacherInfos != null) {
                            for (int k = 0; k < TeacherInfos.length(); k++) {
                                JSONObject jTeacher = null;
                                try {
                                    jTeacher = TeacherInfos.getJSONObject(k);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Teacher = jTeacher.getString("title");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (Teacher == null) {
                            Teacher = "Aucun intervenant";
                        }

                        if (Seats == null) {
                            Seats = "-/-";
                        }

                        Intent intentPlanningView = new Intent(PlanningView.this, PlanningInscriptionView.class);
                        intentPlanningView.putExtra(EXTRA_MODULE, UserModuleInfos);
                        intentPlanningView.putExtra(EXTRA_ACTIVITY, planning_title);
                        intentPlanningView.putExtra(EXTRA_PRESENT, UserTokenInfos);
                        intentPlanningView.putExtra(EXTRA_BEGIN, start);
                        intentPlanningView.putExtra(EXTRA_END, end);
                        intentPlanningView.putExtra(EXTRA_ROOM, Room);
                        intentPlanningView.putExtra(EXTRA_SEATS, Seats);
                        intentPlanningView.putExtra(EXTRA_PROF, Teacher);
                        intentPlanningView.putExtra(EXTRA_REGISTER, UserRegisterStudentInfos);
                        intentPlanningView.putExtra(EXTRA_ALLOW, UserAllowRegisterInfos);
                        intentPlanningView.putExtra(EXTRA_CODEM, CodeModule);
                        intentPlanningView.putExtra(EXTRA_CODEI, CodeInstance);
                        intentPlanningView.putExtra(EXTRA_CODEA, CodeActivity);
                        intentPlanningView.putExtra(EXTRA_CODEE, CodeEvent);
                        startActivity(intentPlanningView);
                    }
                }
            }
        });
    }

    public void PrevDay_OnClick(View v) {
        Intent intentPrevDay = new Intent(PlanningView.this, PlanningView.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tmp = new GregorianCalendar();
        tmp.setTime(DateCurrent);
        if (sdf.format(tmp.getTime()).equals(sdf.format(StartWeek.getTime()))) {
            StartWeek.add(Calendar.WEEK_OF_MONTH, 0);
            EndWeek.add(Calendar.WEEK_OF_MONTH, 0);
            nb_week = -1;
        }
        tmp.add(Calendar.DATE, -1);
        String date = sdf.format(tmp.getTime());
        String start = sdf.format(StartWeek.getTime());
        String end = sdf.format(EndWeek.getTime());
        intentPrevDay.putExtra(EXTRA_TOKEN, Token);
        intentPrevDay.putExtra(EXTRA_SEMESTER, UserSemester);
        intentPrevDay.putExtra(EXTRA_DATE, date);
        intentPrevDay.putExtra(EXTRA_BWEEK, "null");
        intentPrevDay.putExtra(EXTRA_EWEEK, "null");
        startActivity(intentPrevDay);
    }

    public void NextDay_OnClick(View v) {
        Intent intentNextDay = new Intent(PlanningView.this, PlanningView.class);
        Calendar tmp = new GregorianCalendar();
        tmp.setTime(DateCurrent);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (sdf.format(tmp.getTime()).equals(sdf.format(EndWeek.getTime()))) {
            StartWeek.add(Calendar.WEEK_OF_MONTH, 0);
            EndWeek.add(Calendar.WEEK_OF_MONTH, 0);
            nb_week = 1;
        }
        tmp.add(Calendar.DATE, 1);
        String date = sdf.format(tmp.getTime());
        String start = sdf.format(StartWeek.getTime());
        String end = sdf.format(EndWeek.getTime());
        intentNextDay.putExtra(EXTRA_TOKEN, Token);
        intentNextDay.putExtra(EXTRA_SEMESTER, UserSemester);
        intentNextDay.putExtra(EXTRA_DATE, date);
        intentNextDay.putExtra(EXTRA_BWEEK, "null");
        intentNextDay.putExtra(EXTRA_EWEEK, "null");
        startActivity(intentNextDay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning_view, menu);
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
        this.Planning = (ListView)findViewById(R.id.PlanningList);
    }

}
