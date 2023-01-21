package com.example.wally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomePage extends AppCompatActivity {

    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        String [] userValues = intent.getStringArrayExtra("userValues");
        User user = new User(userValues[0],userValues[1], userValues[2],userValues[3],userValues[4]);


        txtWelcome = (TextView) findViewById(R.id.textView2);
        txtWelcome.setText("Welcome "+user.firstName);
    }
}