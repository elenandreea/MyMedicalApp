package com.ibm.mymedicalapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import com.ibm.mymedicalapp.Adapters.SymptomAdapter;
import com.ibm.mymedicalapp.Models.Symptom;
import com.ibm.mymedicalapp.R;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DiagnoseActivity extends AppCompatActivity {

    RecyclerView symptomRecyclerView;
    SymptomAdapter symptomAdapter;
    List<Symptom> symptomList;

    Button evaluateBtn;
    TextView infoTxt;

    @Override
    protected void onStart() {
        super.onStart();
        readDataFromCSV();
    }

    private void readDataFromCSV() {
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

//        downloadCustomMLModel();

        symptomRecyclerView = findViewById(R.id.recycler_view_symptoms);
        symptomRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        symptomRecyclerView.setHasFixedSize(true);

        infoTxt = findViewById(R.id.select_symptom_text);
        evaluateBtn = findViewById(R.id.evaluate_btn);

    }

    private void downloadCustomMLModel() {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("my_first_model", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions) //LOCAL_MODEL
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel model) {
                        // Download complete. Depending on your app, you could enable the ML
                        // feature, or switch from the local model to the remote model, etc.

                        // The CustomModel object contains the local path of the model file,
                        // which you can use to instantiate a TensorFlow Lite interpreter.
                        File modelFile = model.getFile();
                        if (modelFile != null) {
                            Interpreter interpreter = new Interpreter(modelFile);
                        }
                    }
                });
    }
}
