package com.example.a1jengm22.networkcomms;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    class MyArtist extends AsyncTask<String,Void,String>
    {
        public String doInBackground(String... artist)
        {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?format=json&artist=" + artist[0]);
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if(conn.getResponseCode() == 200)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while((line = br.readLine()) !=null)
                        result += line;

                    // result will contain JSON

                    try
                    {
                        String parsedData = "";


                        JSONArray jsonArr = new JSONArray(result);

                        for (int i = 0; i < jsonArr.length(); i++)
                        {

                            JSONObject curObj = jsonArr.getJSONObject(i);
                            String songTitle = curObj.getString(("songTitle")),
                                    Artist = curObj.getString("artist"),
                                    Year = curObj.getString("year"),
                                    Month = curObj.getString("month");
                        }

                    }
                    catch (JSONException e)
                    {
                        return e.toString();
                    }
                }
                else
                    return "HTTP ERROR: " + conn.getResponseCode();


            }
            catch(IOException e)
            {

                return e.toString();
            }
            finally
            {
                if(conn!=null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result)
        {
            TextView tv = (TextView)findViewById(R.id.tv1);
            EditText et = (EditText)findViewById(R.id.et1);
            tv.setText(result);
            et.setText(result);
        }



}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button go = (Button)findViewById(R.id.btn1);
        go.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        MyArtist t = new MyArtist();
        EditText et = (EditText)findViewById(R.id.et1);
        String artist = et.getText().toString();
        t.execute(artist);
    }
}



