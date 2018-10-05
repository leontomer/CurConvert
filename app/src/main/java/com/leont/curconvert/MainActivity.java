package com.leont.curconvert;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    float num;
    public class DownloadTask extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
            DecimalFormat df = new DecimalFormat("#.00");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            DownloadTask task = new DownloadTask();
            String result = null;
            try {
                result = task.execute("http://www.floatrates.com/daily/usd.json").get();
                JSONObject jsonObject = new JSONObject(result);

                JSONObject ils = jsonObject.getJSONObject("ils");

                Log.i("content", ils.getString("rate"));

num=Float.parseFloat(ils.getString("rate"));


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            public void bt(View v){
        Button b=(Button) findViewById(v.getId());
        EditText e=findViewById(R.id.editText);
        e.getText().append(b.getText().toString());
    }


    public void Convert(View v){
        EditText e = (EditText) findViewById(R.id.editText);
        if(e.getText().length()==0)
            return;
        if(e.getText().toString().equals("."))
            return;
        double n=Double.parseDouble(e.getText().toString());
        n=n*num;
        String s = String.valueOf(n);
        Toast.makeText(MainActivity.this, s+" Shekels", Toast.LENGTH_LONG).show();





    }


    public void del(View view){

        EditText e = (EditText) findViewById(R.id.editText);
        if(e.getText().length()==0)
            return;
        String text = e.getText().toString();
        e.setText(text.substring(0, text.length() - 1));
    }
}




