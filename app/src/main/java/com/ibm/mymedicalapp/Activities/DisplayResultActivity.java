package com.ibm.mymedicalapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.mymedicalapp.Models.Disease;
import com.ibm.mymedicalapp.R;

public class DisplayResultActivity extends AppCompatActivity {
    TextView resultTextViewTitle, resultTextViewDescription, resultTextViewPrevention;
    Button backBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        resultTextViewTitle = findViewById(R.id.result_title);
        resultTextViewDescription = findViewById(R.id.result_description);
        resultTextViewPrevention = findViewById(R.id.result_prevention);
        backBtn = findViewById(R.id.back_btn);

        int result = getIntent().getExtras().getInt("result");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Diseases").child(String.valueOf(result));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Disease disease = snapshot.getValue(Disease.class);
                resultTextViewTitle.setText(disease.getDiseaseName());
                resultTextViewDescription.setText(disease.getDiseaseDescription());
                resultTextViewPrevention.setText(String.format("Precautions: %s", disease.getDiseasePrecautions()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));


    }
}
