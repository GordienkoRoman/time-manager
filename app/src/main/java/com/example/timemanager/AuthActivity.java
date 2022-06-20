package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.timemanager.Classes.Action;

import java.util.ArrayList;


public class AuthActivity extends AppCompatActivity {
    String name;

   static public ArrayList<Action> actionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if(name!=null? name.equals(""):name!=null)
            return;
    }

}