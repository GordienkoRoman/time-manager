package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity {

    Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Intent intent = getIntent();
        TextView textName = findViewById(R.id.actionName);
        textName.setText(intent.getStringExtra("name"));

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this,alarmUri);
        if(ringtone==null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this,alarmUri);
        }
        if(ringtone!=null)
            ringtone.play();
    }

    @Override
    protected void onDestroy() {
        if(ringtone.isPlaying())
            ringtone.stop();
        super.onDestroy();
    }
}