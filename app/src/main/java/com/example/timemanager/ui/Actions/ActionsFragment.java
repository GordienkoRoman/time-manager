package com.example.timemanager.ui.Actions;

import static com.example.timemanager.Activity2.actionList;
import static com.example.timemanager.Activity2.overWriteFIle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timemanager.Activity2;
import com.example.timemanager.Adapters.ListAdapter;
import com.example.timemanager.AlarmActivity;
import com.example.timemanager.Classes.Action;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentActionBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment implements View.OnClickListener {

    com.example.timemanager.databinding.FragmentActionBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    Context ctx;
    Button but_start,but_cancel;
    ListAdapter listAdapter;


    public ActionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ActionsFragment newInstance(String param1, String param2) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ctx = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentActionBinding.inflate(inflater, container, false);
        binding.listViewActions.setClickable(true);
        binding.listViewActions.setOnItemClickListener((parent, view, position, id) ->
                Navigation.findNavController(view).navigate(
                        ActionsFragmentDirections.navigateToCreatActions(actionList.get(position).getName(),
                                actionList.get(position).getTime().getTime().getHours(),
                                actionList.get(position).getTime().getTime().getMinutes(),
                                position)));
        binding.listViewActions.setOnItemLongClickListener((parent, view, position, id) -> {
            removeAction(position);
            return false;
        });
        View view = binding.getRoot();
        FloatingActionButton but_add = view.findViewById(R.id.but_add_action);
        listAdapter = new ListAdapter(getActivity(), actionList);
        binding.listViewActions.setAdapter(listAdapter);
        but_add.setOnClickListener(v -> Navigation.findNavController(v).navigate(ActionsFragmentDirections.navigateToCreatActions("", -1, 0, 0)));
        but_start = view.findViewById(R.id.but_start);
        but_cancel = view.findViewById(R.id.but_cancel);
        but_start.setOnClickListener(this);
        but_cancel.setOnClickListener(this);
        return view;
    }

    void removeAction(int position) {
        actionList.remove(position);
        listAdapter.notifyDataSetChanged();
        overWriteFIle(getActivity().getBaseContext());
    }


    private PendingIntent getAlarmPendingIntent(int i) {
        Intent alarmIntent = new Intent(getContext(), Activity2.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(getContext(), i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getAlarmPendigIntent2(int i, String name) {
        Intent alarmIntent = new Intent(getContext(), AlarmActivity.class);
        alarmIntent.putExtra("name", name);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(getContext(), i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    void switchAlarm(Boolean isOn) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int i = 0, temph, tempm;
        Calendar cureentTime = Calendar.getInstance();
        for (Action a : actionList) {
            temph = a.getTime().getTime().getHours();
            tempm = a.getTime().getTime().getMinutes();
            cureentTime.add(Calendar.HOUR_OF_DAY, temph);
            cureentTime.add(Calendar.MINUTE, tempm);
            cureentTime.setTimeInMillis(cureentTime.getTimeInMillis());
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(cureentTime.getTimeInMillis(), getAlarmPendingIntent(a.getId()));
            actionList.get(i).setInfo(alarmClockInfo);
            if (isOn)
            {
                alarmManager.cancel(getAlarmPendigIntent2(a.getId(), a.getName()));
                actionList.get(i).setInfo(null);
            }
            else
            {
                alarmManager.setAlarmClock(alarmClockInfo, getAlarmPendigIntent2(a.getId(), a.getName()));
                actionList.get(i).setInfo(alarmClockInfo);
            }
            i++;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_start: {
                switchAlarm(false);
                break;
            }
            case R.id.but_cancel: {
                switchAlarm(true);
                break;
            }
        }
    }
}
