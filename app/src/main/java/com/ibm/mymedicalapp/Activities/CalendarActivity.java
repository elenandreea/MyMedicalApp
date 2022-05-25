package com.ibm.mymedicalapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.mymedicalapp.Adapters.AlarmAdapter;
import com.ibm.mymedicalapp.Models.Alarm;
import com.ibm.mymedicalapp.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;
    private RecyclerView alarmRecyclerView;

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alarmList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()){
                    Alarm alarm = snap.getValue(Alarm.class);
                    alarmList.add(alarm);
                }

                alarmAdapter = new AlarmAdapter(CalendarActivity.this, alarmList);
                alarmRecyclerView.setAdapter(alarmAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Alarms").child(currentUser.getUid());

        alarmRecyclerView = findViewById(R.id.recycle_view_alarm);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        alarmRecyclerView.setHasFixedSize(true);

        FloatingActionButton alarmFab = findViewById(R.id.add_alarm_fab);
        alarmFab.setOnClickListener(v -> {
            Intent newAlarmIntent = new Intent(CalendarActivity.this, AddReminderActivity.class);
            newAlarmIntent.putExtra("action", "create");
            startActivity(newAlarmIntent);
        });
    }
}
