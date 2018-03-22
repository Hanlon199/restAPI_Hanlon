package com.example.hanlo.restapi_hanlon;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;


public class MainActivity extends Activity {

    private String mashKey = "zA9LY0fNXNmsh1SOJaOaqsArk9rIp13STo8jsn6YKY40V2zZ3t";
    private String urlParam = "";
    private String json = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //called on click of the main button that takes the two names and sends them to the API
    //the sends the name and results to then next page
    public void onCalculateLove(View view){
        //get edit text boxes
        EditText firstname  = (EditText) findViewById(R.id.first_name_input);
        EditText secondname = (EditText) findViewById(R.id.second_name_input);
        //convert the textfield to a string value
        String firstinput = firstname.getText().toString().trim();
        String secondinput = secondname.getText().toString().trim();
        System.out.println("FirstInput:" + firstinput);
        System.out.println("SecondInput:" + secondinput);
        //create the concatenated url params for API
        urlParam = "https://love-calculator.p.mashape.com/getPercentage?fname="+ firstinput +"&sname=" + secondinput +"&mashape-key="+ mashKey;
        //Go to async task
        new JSONTask().execute(urlParam);



    }

    //classs to take url and get data in JSON format from API
    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //create url connection and buffered reader to connect and readliens from the API
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                //open url connection
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                //get input stream
                InputStream stream = connection.getInputStream();

                //create buffered reader to read lines of data
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                try {
                    json = buffer.toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return buffer.toString();
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            finally {
                //try blocks to wrap up the server connection and close the reader
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //after JSON data has been grabbed execute the intent
            String result = null;
            String percentage = null;
            try {
                    //parse the JSON object into Result and Percentage
                    System.out.println("JSON: " + json);
                    JSONObject jsonObject = new JSONObject(json);
                    result = jsonObject.getString("result");
                    percentage = jsonObject.getString("percentage");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Create intent and send it to the result activity
            intent = new Intent(MainActivity.this, ResultActivity.class);
            //add extra information onto the intent
            intent.putExtra(ResultActivity.result, result);
            intent.putExtra(ResultActivity.percentage, percentage);
            //begin intent
            startActivity(intent);
        }
    }



}


