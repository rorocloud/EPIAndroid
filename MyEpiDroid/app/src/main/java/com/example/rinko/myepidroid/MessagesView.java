package com.example.rinko.myepidroid;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


public class MessagesView extends ActionBarActivity {
    IntraManager im = new IntraManager();
    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView content1;
    private TextView content2;
    private TextView content3;
    private TextView date1;
    private TextView date2;
    private TextView date3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_view);
        setup_variables();
    }

    void setup_variables()
    {
        title1 = (TextView)findViewById(R.id.title_textView);
        title2 = (TextView)findViewById(R.id.title2_textView);
        title3 = (TextView)findViewById(R.id.title3_textView);
        content1 = (TextView)findViewById(R.id.content_textView);
        content2 = (TextView)findViewById(R.id.content2_textView);
        content3 = (TextView)findViewById(R.id.content3_textView);
        date1 = (TextView)findViewById(R.id.date_textView);
        date2 = (TextView)findViewById(R.id.date2_textView);
        date3 = (TextView)findViewById(R.id.date3_textView);
    }

    public void setup_messages(String res) throws JSONException {
        title1 = (TextView)findViewById(R.id.title_textView);
        title2 = (TextView)findViewById(R.id.title2_textView);
        title3 = (TextView)findViewById(R.id.title3_textView);
        content1 = (TextView)findViewById(R.id.content_textView);
        content2 = (TextView)findViewById(R.id.content2_textView);
        content3 = (TextView)findViewById(R.id.content3_textView);
        date1 = (TextView)findViewById(R.id.date_textView);
        date2 = (TextView)findViewById(R.id.date2_textView);
        date3 = (TextView)findViewById(R.id.date3_textView);
        JSONArray MessagesArray = null;
        try {
            MessagesArray = new JSONArray(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        title1.setText(MessagesArray.getJSONObject(1).getString("title"));
        title2.setText(MessagesArray.getJSONObject(2).getString("title"));
        title3.setText(MessagesArray.getJSONObject(3).getString("title"));
        content1.setText(MessagesArray.getJSONObject(1).getString("content"));
        content2.setText(MessagesArray.getJSONObject(2).getString("content"));
        content3.setText(MessagesArray.getJSONObject(3).getString("content"));
        date1.setText(MessagesArray.getJSONObject(1).getString("date"));
        date2.setText(MessagesArray.getJSONObject(2).getString("date"));
        date3.setText(MessagesArray.getJSONObject(3).getString("date"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages_view, menu);
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
