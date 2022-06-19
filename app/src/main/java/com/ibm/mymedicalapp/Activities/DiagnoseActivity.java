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
import retrofit2.Response;

public class DiagnoseActivity extends AppCompatActivity {

    RecyclerView symptomRecyclerView;
    SymptomAdapter symptomAdapter;
    List<Symptom> symptomList;
    Map<Integer,String> diseaseDictionary;

    Button evaluateBtn;
    TextView infoTxt;
    Intent intent;
    TextView helpTxt;
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
        helpTxt = findViewById(R.id.help);
        evaluateBtn = findViewById(R.id.evaluate_btn);
        evaluateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopulateDiseaseDB populateDiseaseDB = new PopulateDiseaseDB();
//                populateDiseaseDB.readExcelDisease();
                List<Integer> checkedBoxList = symptomAdapter.getSelectedSymptoms();
                inputSymptoms = createInputArray(checkedBoxList);
//                returnDiagnosisUsingThread();
                int result = returnDiagnosisUsingExecutor();
                System.out.println("lalalallala:" + result);
                intent = new Intent(getApplicationContext(), DisplayResultActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });

    }

    class DiagnoseRunnable implements Runnable{
        private volatile int value;

        @Override
        public void run() {
            value = Integer.parseInt(getDiagnosisResult());
//            runOnUiThread(() -> helpTxt.setText(value));
        }

        public int getValue() {
            return value;
        }
    }

    DiagnoseRunnable diagnoseRunnable = new DiagnoseRunnable();

    private int returnDiagnosisUsingExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(diagnoseRunnable);
        try {
            executorService.awaitTermination(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return diagnoseRunnable.getValue();
    }

    private void returnDiagnosisUsingThread() {
        new Thread(diagnoseRunnable).start();
    }

    private String getDiagnosisResult(){
        String result ="";
        try {
            Response<String> response = ServerServiceProvider.createRetrofitService().getDiagnose(inputSymptoms).execute();
            if (response.body() != null) {
                result = response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
