package com.example.timemanager;



import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.timemanager.Adapters.ListAdapter;
import com.example.timemanager.Classes.Action;
import com.example.timemanager.databinding.Activity2Binding;
import com.example.timemanager.restAPI.InsultModel;
import com.example.timemanager.restAPI.OnFetchDataListener;
import com.example.timemanager.restAPI.RequestManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;



public class Activity2 extends AppCompatActivity {
    static public ArrayList<Action> actionList = new ArrayList<>();

    public static String insultString="FUCK";
    private Activity2Binding binding;

    public  static void removeAction(int position){
        actionList.remove(position);
       // listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        new GetURLData().execute();
        binding = Activity2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_bottom_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigationActions, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_2);
        //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navBottomView, navController);
        fillList(this);
    }
    public static void overWriteFIle(Context ctx)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    ctx.openFileOutput("Actions.txt", Context.MODE_PRIVATE)
            ));
            for (int i = 0; i < actionList.size(); i++) {
                bw.write(actionList.get(i).getName()+"\n");
                bw.write(actionList.get(i).getTime().getTime().getHours()+"\n");
                bw.write(actionList.get(i).getTime().getTime().getMinutes()+"\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }
    public static void fillList(Context ctx) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    ctx.openFileInput("Actions.txt")
            ));
            actionList.clear();
            String str;
            while ((str = br.readLine()) != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(br.readLine()));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(br.readLine()));
                    actionList.add(new Action(str, "description", calendar,actionList.size()));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }
    private final OnFetchDataListener<InsultModel> listener = new OnFetchDataListener<InsultModel>() {
        @Override
        public void onFetchData(InsultModel insult, String message) {
           insultString = insult.getInsult();
        }

        @Override
        public void onError(String message) {

        }
    };
    private class GetURLData extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestManager manager = new RequestManager(Activity2.this);
            manager.getInsult(listener,"en","json");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}