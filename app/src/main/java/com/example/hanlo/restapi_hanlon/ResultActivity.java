package com.example.hanlo.restapi_hanlon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by hanlo on 3/1/2018.
 */

public class ResultActivity extends Activity{

    public static final String result = "result";
    public static final String percentage = "percentage";
    private static final String na = "N/A";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent       = getIntent();
        String firstInput   = intent.getStringExtra("firstName");
        String secondInput  = intent.getStringExtra("secondName");
        String result       = intent.getStringExtra("result");
        String percent      = intent.getStringExtra("percentage");
        int percentage      = 0;
        System.out.println("result: " + result);

        TextView percentContent   = (TextView) findViewById(R.id.percent_content);
        TextView resultContent    = (TextView) findViewById(R.id.result_content);
        //set the Textviews to the value from the intents
        if(result == null && percent == null){
            resultContent.setText(na);
            percentContent.setText(na);
            findViewById(R.id.love_result).setBackgroundResource(R.drawable.question);
        }
        else{
            percentContent.setText(percent);
            resultContent.setText(result);
            percentage = Integer.parseInt(percent);
            System.out.println("percentage: " + percentage);

        }
        if(percentage < 20){
            findViewById(R.id.love_result).setBackgroundResource(R.drawable.gravestone);
        }
        if(percentage > 20 && percentage < 40){
            findViewById(R.id.love_result).setBackgroundResource(R.drawable.brokenheart);
        }
        if(percentage > 40 && percentage < 80){
            findViewById(R.id.love_result).setBackgroundResource(R.drawable.heart);
        }
        if(percentage > 80){
            findViewById(R.id.love_result).setBackgroundResource(R.drawable.victory);
        }
        System.out.println("Image:" + findViewById(R.id.love_result).getTag());



    }
}
