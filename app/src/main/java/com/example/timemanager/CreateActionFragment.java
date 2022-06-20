package com.example.timemanager;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateActionFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button but_choose_time;
    Context ctx;
    int hour=0, min=0;
    ImageView accept_icon, deny_icon;
    EditText editActionName, editActionDescription;

    public CreateActionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateActionFragment newInstance(String param1, String param2) {
        CreateActionFragment fragment = new CreateActionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_action, container, false);
        but_choose_time = view.findViewById(R.id.but_choose_duration);
        but_choose_time.setOnClickListener(this);
        accept_icon = view.findViewById(R.id.accept_icon);
        accept_icon.setOnClickListener(this);
        deny_icon = view.findViewById(R.id.deny_icon);
        deny_icon.setOnClickListener(this);
        editActionName = view.findViewById(R.id.editActionName);
        editActionDescription=view.findViewById(R.id.editDescription);
        return view;
    }

    String getTime() {
        return "";
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.but_choose_duration): {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;
                        but_choose_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(ctx, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hour, min, true);
                timePickerDialog.setTitle("Choose Duration");
                timePickerDialog.show();
                break;
            }
            case (R.id.accept_icon): {
                String name = editActionName.getText().toString().trim();
                if(name=="")
                    name = "<blank>";
                String time = but_choose_time.getText().toString().trim();

                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            getActivity().getBaseContext().openFileOutput("Actions.txt",Context.MODE_APPEND)
                    ));
                    bw.write(name);
                    bw.write("  ");
                    bw.write(hour+"");
                    bw.write("  ");
                    bw.write(min+"");
                    bw.write("\n");
                    bw.close();
                    Navigation.findNavController(v).navigate(R.id.action_createActionFragment_to_navigation_actions);
                } catch (IOException e) {
                    System.out.println("error");
                }
                break;
            }
        }

    }
}
