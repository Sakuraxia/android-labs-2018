package edu.hzuapps.androidlabs.soft1614080902337;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonActivity extends AppCompatActivity {

    public  String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //网络编程获取Json
    public void getJson()
    {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String url_s = "https://raw.githubusercontent.com/DcXuhm/android-labs-2018/master/soft1614080902337/app/assets/text.json";
                        try {
                            URL url = new URL(url_s);
                            HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                            conn.setConnectTimeout(5000);
                            conn.setUseCaches(false);

                            conn.connect();
                            InputStream inputStream = conn.getInputStream();
                            InputStreamReader reader = new InputStreamReader(inputStream);
                            BufferedReader buffer = new BufferedReader(reader);
                            if(conn.getResponseCode()==200)
                            {
                                String inputline;
                                StringBuffer resultdata = new StringBuffer();
                                while ((inputline = buffer.readLine())!=null)
                                {
                                    resultdata.append(inputline);
                                }
                                text=resultdata.toString();
                                Log.v("out---------------->",text);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    public void parseJson()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    JSONArray jsonstudents = jsonObject.getJSONArray("students");
                    String test;
                    for(int i=0;i<jsonstudents.length();i++)
                    {
                        JSONObject students = jsonstudents.getJSONObject(i);
                        Log.d("The student is ",
                                students.getString("name")+","+students.getString("Sex")+","+students.getInt("age"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
