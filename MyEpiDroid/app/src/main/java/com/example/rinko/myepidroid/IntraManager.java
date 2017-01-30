package com.example.rinko.myepidroid;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IntraManager {
    HttpPost    post;
    HttpGet     get;
    HttpDelete  delete;
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    InputStream responseInStream;
    String ResponseStr;

    private void Send_POST_Request() {
        Log.d("MyActivity", "HERE PAIRS >" + pairs.toString());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    HttpResponse response = client.execute(post);
                    ResponseStr = EntityUtils.toString(response.getEntity());
                    Log.d("MyActivity", ResponseStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
/* get */
    private void Send_GET_Request() {
    /*exemple : http://epitech-api.herokuapp.com/projects?token=csreir33kdn7fkp8ip78620a55*/
        Log.d("MyActivity", "GET HERE PAIRS >" + pairs.toString());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    HttpResponse response = client.execute(get);
                    ResponseStr = EntityUtils.toString(response.getEntity());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        while(t.isAlive()){}
    }
    private void Send_DELETE_Request() {
        Log.d("MyActivity", "HERE PAIRS >" + pairs.toString());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                try {
                    HttpResponse response = client.execute(delete);
                    ResponseStr = EntityUtils.toString(response.getEntity());
                    Log.d("MyActivity", ResponseStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    //METHODE WHICH USE POST REQUEST
    public String login(String loginStr, String passwordStr) {
        post = new HttpPost("http://epitech-api.herokuapp.com/login");
        pairs.removeAll(pairs);
        pairs.add(new BasicNameValuePair("login", loginStr));
        pairs.add(new BasicNameValuePair("password", passwordStr));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        return (ResponseStr);
    }

    public String infos(String tokenStr) {
        ResponseStr = null;
        post = new HttpPost("http://epitech-api.herokuapp.com/infos");
        pairs.add(new BasicNameValuePair("token", tokenStr));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        pairs.clear();
        return (ResponseStr);

    }

    public String submodule(String token, String scolaryear, String codemodule, String codeinstance) {
        post = new HttpPost("http://epitech-api.herokuapp.com/module");
        //pairs.removeAll(pairs);
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codemodule));
        pairs.add(new BasicNameValuePair("codeinstance", codeinstance));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        return (ResponseStr);
    }

    //METHODE WHICH USE DELETE REQUEST
    public String unsubmodule(String token, String scolaryear, String codemodule, String codeinstance){
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/module?";
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codemodule));
        pairs.add(new BasicNameValuePair("codeinstance", codeinstance));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        System.out.println(Request);
        delete = new HttpDelete(Request);
        Send_DELETE_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }


    // METHODE WHICH USE GET REQUEST
    public String photo(String tokenStr, String loginStr){
    ResponseStr = null;
    String Request = "http://epitech-api.herokuapp.com/photo?";
    pairs.add(new BasicNameValuePair("token", tokenStr));
    pairs.add(new BasicNameValuePair("login", loginStr));

    String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
    Request += pairsStr;
    get = new HttpGet(Request);
    Send_GET_Request();
    while (ResponseStr == null){}
    pairs.clear();
    return (ResponseStr);
    }

    public String user(String tokenStr, String Login) {
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/user?";
        pairs.add(new BasicNameValuePair("token", tokenStr));
        pairs.add(new BasicNameValuePair("user", Login));
        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null) {
        }
        pairs.clear();
        return (ResponseStr);

    }

    public String trombi(String tokenStr, String loginStr, String year, String location, int offset){
        ResponseStr = null;
        String photoURL = "http://epitech-api.herokuapp.com/trombi?";
        pairs.add(new BasicNameValuePair("token", tokenStr));
        pairs.add(new BasicNameValuePair("year", "2015"));
        pairs.add(new BasicNameValuePair("location", location));
        //   pairs.add(new BasicNameValuePair("offset",String.valueOf(offset)));
        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        photoURL += pairsStr;
        get = new HttpGet(photoURL);
        Send_GET_Request();

        pairs.clear();
        Log.d("MyActivity", ResponseStr);
        return (ResponseStr);
    }

    public String Planning(String tokenStr, String start, String end){
        ResponseStr = null;
        String photoURL = "http://epitech-api.herokuapp.com/planning?";
        pairs.add(new BasicNameValuePair("token", tokenStr));
        Log.d("MyActivity", tokenStr);

        pairs.add(new BasicNameValuePair("start", start));
        pairs.add(new BasicNameValuePair("end", end));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        photoURL += pairsStr;
        get = new HttpGet(photoURL);
        Send_GET_Request();

        pairs.clear();
        Log.d("MyActivity",ResponseStr);
        return (ResponseStr);
    }


    public String messages(String tokenStr){
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/messages?";
        pairs.add(new BasicNameValuePair("token", tokenStr));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }

    public String projects(String tokenStr){
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/projects?";
        pairs.add(new BasicNameValuePair("token", tokenStr));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }

    public String allmodules(String tokenStr, String scolaryears, String location, String course){
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/allmodules?";
        pairs.add(new BasicNameValuePair("token", tokenStr));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryears));
        pairs.add(new BasicNameValuePair("location", location));
        pairs.add(new BasicNameValuePair("course", course));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }

    public String modulesGrade(String tokenStr){
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/modules?";
        pairs.add(new BasicNameValuePair("token", tokenStr));
        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }


    public String unsubevent(String token, String scolaryear, String codeM, String codeI, String codeA, String codeE) {
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/event?";
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codeM));
        pairs.add(new BasicNameValuePair("codeinstance", codeI));
        pairs.add(new BasicNameValuePair("codeacti", codeA));
        pairs.add(new BasicNameValuePair("codeevent", codeE));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        delete = new HttpDelete(Request);
        Send_DELETE_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }

    public String subevent(String token, String scolaryear, String codeM, String codeI, String codeA, String codeE) {
        post = new HttpPost("http://epitech-api.herokuapp.com/event");
        //pairs.removeAll(pairs);
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codeM));
        pairs.add(new BasicNameValuePair("codeinstance", codeI));
        pairs.add(new BasicNameValuePair("codeacti", codeA));
        pairs.add(new BasicNameValuePair("codeevent", codeE));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        return (ResponseStr);
    }

    public String validateToken(String token, String scolaryear, String codeM, String codeI, String codeA, String codeE, String MyToken) {
        post = new HttpPost("http://epitech-api.herokuapp.com/token");
        //pairs.removeAll(pairs);
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codeM));
        pairs.add(new BasicNameValuePair("codeinstance", codeI));
        pairs.add(new BasicNameValuePair("codeacti", codeA));
        pairs.add(new BasicNameValuePair("codeevent", codeE));
        pairs.add(new BasicNameValuePair("tokenvalidationcode", MyToken));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        return (ResponseStr);
    }

    public String alert(String token) {
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/alerts?";
        pairs.add(new BasicNameValuePair("token", token));
        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        get = new HttpGet(Request);
        Send_GET_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }

    public String subproject(String token, String scolaryear, String codem, String codei, String codea) {
        post = new HttpPost("http://epitech-api.herokuapp.com/project");
        //pairs.removeAll(pairs);
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codem));
        pairs.add(new BasicNameValuePair("codeinstance", codei));
        pairs.add(new BasicNameValuePair("codeacti", codea));
        Send_POST_Request();
        while (ResponseStr == null) {
        }
        return (ResponseStr);
    }

    public String unsubproject(String token, String scolaryear, String codem, String codei, String codea) {
        ResponseStr = null;
        String Request = "http://epitech-api.herokuapp.com/project?";
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("scolaryear", scolaryear));
        pairs.add(new BasicNameValuePair("codemodule", codem));
        pairs.add(new BasicNameValuePair("codeinstance", codei));
        pairs.add(new BasicNameValuePair("codeacti", codea));

        String pairsStr = URLEncodedUtils.format(pairs, "utf-8");
        Request += pairsStr;
        delete = new HttpDelete(Request);
        Send_DELETE_Request();
        while (ResponseStr == null){}
        pairs.clear();
        return (ResponseStr);
    }
}