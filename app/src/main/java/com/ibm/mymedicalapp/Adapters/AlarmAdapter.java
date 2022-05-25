package com.ibm.mymedicalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.mymedicalapp.Activities.AddReminderActivity;
import com.ibm.mymedicalapp.Models.Alarm;

import com.ibm.mymedicalapp.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context mContext;
    private List<Alarm> alarmsData;

    public AlarmAdapter(Context mContext, List<Alarm> mData) {
        this.mContext = mContext;
        this.alarmsData = mData;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_alarm_item,parent,false);
        return new AlarmAdapter.AlarmViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.alarmTitle.setText(alarmsData.get(position).getTitle());
        holder.alarmQuantity.setText(alarmsData.get(position).getQuantity());
        String dateAndTime = alarmsData.get(position).getDate() + " " + alarmsData.get(position).getTime();
        holder.alarmDateAndTime.setText(dateAndTime);
        String repeatInfo = setReminderRepeatInfo(alarmsData.get(position).getmRepeat(),
                alarmsData.get(position).getmRepeatNo(), alarmsData.get(position).getmRepeatType());
        holder.alarmRepeatInfo.setText(repeatInfo);
        setActiveImage(alarmsData.get(position).getmActive(),holder.alarmActiveImage);
    }

    @Override
    public int getItemCount() {
        return alarmsData.size();
    }

    public class AlarmViewHolder extends  RecyclerView.ViewHolder {
        private TextView alarmTitle, alarmQuantity, alarmDateAndTime, alarmRepeatInfo;
        private ImageView alarmActiveImage ;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            alarmTitle = itemView.findViewById(R.id.alarm_title);
            alarmQuantity = itemView.findViewById(R.id.alarm_pill_quantity);
            alarmDateAndTime = itemView.findViewById(R.id.alarm_date_time);
            alarmRepeatInfo = itemView.findViewById(R.id.alarm_repeat_info);
            alarmActiveImage = itemView.findViewById(R.id.active_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editIntent = new Intent(mContext, AddReminderActivity.class);
                    int position = getAdapterPosition();

                    editIntent.putExtra("action", "edit");
                    editIntent.putExtra("alarm_id", alarmsData.get(position).getAlarmID());
                    mContext.startActivity(editIntent);
                }
            });
        }

    }

    // Set repeat views
    public String setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if(repeat.equals("true")){
            return "Every " + repeatNo + " " + repeatType + "(s)";
        }else {
            return "Repeat off";
        }
    }

    // Set active image as on or off
    public void setActiveImage(String active, ImageView view){
        if(active.equals("true")){
            view.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (active.equals("false")) {
            view.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }

    }

}
