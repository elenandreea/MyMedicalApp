package com.ibm.mymedicalapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.mymedicalapp.Adapters.SymptomAdapter;
import com.ibm.mymedicalapp.Models.Symptom;
import com.ibm.mymedicalapp.R;
import com.ibm.mymedicalapp.Services.ServerServiceProvider;
import com.ibm.mymedicalapp.Utils.PopulateDiseaseDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnoseActivity extends AppCompatActivity {

    RecyclerView symptomRecyclerView;
    SymptomAdapter symptomAdapter;
    List<Symptom> symptomList;
    Map<Integer,String> diseaseDictionary;

    Button evaluateBtn;
    TextView infoTxt;
    Intent intent;
    int[] inputSymptoms;

    @Override
    protected void onStart() {
        super.onStart();
        readSymptomsFromCSV();
    }

    private void readSymptomsFromCSV() {
        symptomList = new ArrayList<>();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("symptoms_edited.csv"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine = "";
            String category = "";

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] tokens = nextLine.split(",");
                if (category.equals(tokens[1])){
                    symptomList.add(new Symptom(tokens[0], "", Integer.parseInt(tokens[2])));
                }else {
                    symptomList.add(new Symptom(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
                    category = tokens[1];
                }
            }

            System.out.println(symptomList.size());
            symptomAdapter = new SymptomAdapter(DiagnoseActivity.this, symptomList);
            symptomRecyclerView.setAdapter(symptomAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        symptomRecyclerView = findViewById(R.id.recycler_view_symptoms);
        symptomRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        symptomRecyclerView.setHasFixedSize(true);

        infoTxt = findViewById(R.id.select_symptom_text);
        evaluateBtn = findViewById(R.id.evaluate_btn);
        evaluateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopulateDiseaseDB populateDiseaseDB = new PopulateDiseaseDB();
//                populateDiseaseDB.readExcelDisease();
                List<Integer> checkedBoxList = symptomAdapter.getSelectedSymptoms();
                inputSymptoms = createInputArray(checkedBoxList);
                getResultAsyncAndSendItToActivity();
            }
        });

    }

    private void getResultAsyncAndSendItToActivity(){
        ServerServiceProvider.createRetrofitService().getDiagnose(inputSymptoms).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String result = response.body();
                    intent = new Intent(getApplicationContext(), DisplayResultActivity.class);
                    intent.putExtra("result", Integer.parseInt(result));
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private int[] createInputArray(List<Integer> checkedBoxList) {
        int[] myArray = new int[132];
        for (int i = 0; i < 132; i++){
            if (checkedBoxList.contains(i) && i!= 0)
                myArray[i - 1] = 1;
        }
        return myArray;
    }
}
