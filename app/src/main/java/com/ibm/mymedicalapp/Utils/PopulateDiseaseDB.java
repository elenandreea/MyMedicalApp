package com.ibm.mymedicalapp.Utils;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ibm.mymedicalapp.Interfaces.DiagnoseService;
import com.ibm.mymedicalapp.Models.Disease;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulateDiseaseDB {
    Map<String, Disease> diseases;
    Context context;

    public PopulateDiseaseDB(Context context){
        diseases = new HashMap<>();
        this.context = context;
    }

    public Map<String, Disease> getDiseases(){
        return diseases;
    }

    public void readExcelDisease(){
        try {
            InputStream in = context.getAssets().open("symptom_description_final.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(in);
            Workbook workbook = new HSSFWorkbook(myFileSystem);
            Sheet sheet = workbook.getSheetAt(0);
            int rowIndex = 0;

            for (Row row: sheet){  //iteration over row using for each loop
                if (rowIndex != 0) {
                    String name = row.getCell(0).getStringCellValue();
                    String description = row.getCell(1).getStringCellValue();
                    String prevent = row.getCell(2).getStringCellValue() + ", " +
                            row.getCell(3).getStringCellValue() + ", " +
                            row.getCell(4).getStringCellValue() + ", " +
                            row.getCell(5).getStringCellValue();
                    diseases.put(String.valueOf(rowIndex - 1), new Disease(name, description, prevent));
                }
                rowIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postDataToFirebase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Diseases");
        for (Map.Entry<String, Disease> entry: getDiseases().entrySet()){
            databaseReference.child(entry.getKey()).setValue(entry.getValue());
        }
    }

}
