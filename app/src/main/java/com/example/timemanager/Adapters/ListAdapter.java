package com.example.timemanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.timemanager.Classes.Action;
import com.example.timemanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Action> {

    public ListAdapter(@NonNull FragmentActivity context, List<Action> resource) {
        super(context,R.layout.list_action, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Action action = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_action, parent, false);

        TextView actionName = convertView.findViewById(R.id.listActionName);
       // TextView actionDescription = convertView.findViewById(R.id.actionDe);
        TextView actionTime = convertView.findViewById(R.id.listActionTime);
        TextView actionTimeLeft = convertView.findViewById(R.id.listActionTimeLeft);

        actionName.setText(action.getName());
        Calendar calendar = action.getTime();
        Date d = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //actionDescription.setText(action.getDescription());
        actionTime.setText(sdf.format(d));
        calendar= Calendar.getInstance();
        if(action.getInfo()!=null) {
            calendar.setTimeInMillis(action.getInfo().getTriggerTime());
            d = calendar.getTime();
            sdf = new SimpleDateFormat("HH:mm");
            actionTimeLeft.setText(sdf.format(d));
        }

       // actionTime.setText(Integer.toString(d.getHours())+" "+ Integer.toString(d.getMinutes()));

        return convertView;
    }
}
