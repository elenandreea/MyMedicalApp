package com.ibm.mymedicalapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.mymedicalapp.Models.Symptom;
import com.ibm.mymedicalapp.R;

import java.util.ArrayList;
import java.util.List;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.MyViewHolder> {
    Context context;
    List<Symptom> symptomsList;

    public SymptomAdapter(Context context, List<Symptom> symptomsList) {
        this.context = context;
        this.symptomsList = symptomsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_symptom_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category.setText(symptomsList.get(position).getSymptomCategory());
        holder.symptom.setText(symptomsList.get(position).getSymptomName());
        holder.symptom.setChecked(symptomsList.get(position).isChecked());

        holder.symptom.setOnClickListener(v -> {
            boolean newSelected = !symptomsList.get(position).isChecked();
            symptomsList.get(position).setChecked(newSelected);
            holder.symptom.setChecked(newSelected);
        });
    }

    @Override
    public int getItemCount() {
        return symptomsList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        CheckBox symptom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.symptom_category);
            symptom = itemView.findViewById(R.id.symptom_checkbox);
        }
    }

    public ArrayList<Integer> getSelectedSymptoms() {
        ArrayList<Integer> selected = new ArrayList<>();
        for(int i = 0; i < getItemCount(); i++) {
            if(symptomsList.get(i).isChecked()) {
                selected.add(symptomsList.get(i).getSymptomNo());
            }
        }
        return selected;
    }
}
