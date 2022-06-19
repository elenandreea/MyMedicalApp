package com.ibm.mymedicalapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ibm.mymedicalapp.R;

public class DisplayResultActivity extends AppCompatActivity {
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        resultTextView = findViewById(R.id.result);

        int result = getIntent().getExtras().getInt("result");
        resultTextView.setText(String.valueOf(result));


    }
}
