package com.ibm.mymedicalapp.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ibm.mymedicalapp.Interfaces.DiagnoseService;
import com.ibm.mymedicalapp.Models.Disease;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PopulateDiseaseDB {
    List<Disease> diseases;

    public PopulateDiseaseDB(){
        diseases = new ArrayList<>();
    }

    public List<Disease> getDiseases(){
        return diseases;
    }

    public void readExcelDisease(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("D:\\Disertatie_ML\\symptom_description_prelucrat.xlsx"));

            Workbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int rowIndex = 0;

            for (Row row: sheet){  //iteration over row using for each loop
                String name = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                String prevent = row.getCell(2).getStringCellValue() + ", " +
                        row.getCell(3).getStringCellValue() + ", " +
                        row.getCell(4).getStringCellValue() + ", " +
                        row.getCell(5).getStringCellValue();
                diseases.add(new Disease(rowIndex, name, description, prevent));
                rowIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postDataToFirebase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Diseases").push();
        for (Disease d : getDiseases()){
            databaseReference.setValue(d);
        }
    }

}
