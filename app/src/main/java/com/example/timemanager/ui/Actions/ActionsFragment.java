package com.example.timemanager.ui.Actions;

import static com.example.timemanager.AuthActivity.actionList;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.timemanager.Activity2;
import com.example.timemanager.Adapters.ListAdapter;
import com.example.timemanager.AlarmActivity;
import com.example.timemanager.Classes.Action;
import com.example.timemanager.Classes.AlarmReceiver;
import com.example.timemanager.CreateActionFragment;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentActionBinding;
import com.example.timemanager.databinding.FragmentCreateActionBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment implements View.OnClickListener {

    FragmentActionBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context ctx;
    Button but_start;
    ListAdapter listAdapter;
    private AlarmManager alarmManager;


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
        ctx=context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    void fillList() {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getActivity().getBaseContext().openFileInput("Actions.txt")
            ));
            actionList.clear();
            String[] strmas;
            String str, str2 = "";
            while ((str = br.readLine()) != null) {
                {
                    strmas = str.split("  ");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strmas[1]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(strmas[2]));
                    actionList.add(new Action(strmas[0], "description", calendar));
                }
            }

            br.close();
            listAdapter = new ListAdapter(getActivity(), actionList);
            binding.listViewActions.setAdapter(listAdapter);
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentActionBinding.inflate(inflater, container, false);
        binding.listViewActions.setClickable(true);
        binding.listViewActions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeAction(position);
            }
        });
        View view = binding.getRoot();
        fillList();
        FloatingActionButton but_add = view.findViewById(R.id.but_add_action);
        but_add.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_actions_to_createActionFragment));
        but_start = view.findViewById(R.id.but_start);
        but_start.setOnClickListener(this);
        return view;
    }
    void removeAction(int position){
        actionList.remove(position);
        listAdapter.notifyDataSetChanged();

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    getActivity().getBaseContext().openFileOutput("Actions.txt", Context.MODE_PRIVATE)
            ));
            for (int i = 0; i < actionList.size(); i++) {
                bw.write(actionList.get(i).getName());
                bw.write("  ");
                bw.write(actionList.get(i).getTime().getTime().getHours()+"");
                bw.write("  ");
                bw.write(actionList.get(i).getTime().getTime().getMinutes()+"");
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }


    private PendingIntent getAlarmPendigIntent(int i)
    {
        Intent alarmIntent = new Intent(getContext(),Activity2.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(getContext(),i,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private PendingIntent getAlarmPendigIntent2(int i,String name)
    {
        Intent alarmIntent = new Intent(getContext(), AlarmActivity.class);
        alarmIntent.putExtra("name",name);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(getContext(),i,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_start:{
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                int i=0,temph,tempm;
                Calendar curentTime = Calendar.getInstance();
                for (Action a : actionList) {
                    temph=a.getTime().getTime().getHours();
                    tempm=a.getTime().getTime().getMinutes();

                    curentTime.add(Calendar.HOUR_OF_DAY,temph);
                    curentTime.add(Calendar.MINUTE,tempm);
         /*           if(curentTime.before(Calendar.getInstance()))
                        curentTime.add(Calendar.DAY_OF_MONTH,1);*/
                    System.out.println("|||||||||||||||||||||||||||||||||||||||" + curentTime.getTime());
                    AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(curentTime.getTimeInMillis(),getAlarmPendigIntent(i));
                    alarmManager.setAlarmClock(alarmClockInfo,getAlarmPendigIntent2(i,a.getName()));
                    i++;
                    Toast.makeText(ctx,"Alarm set",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}