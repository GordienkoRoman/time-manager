package com.example.timemanager.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemanager.Activity2;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
TextView textHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //final TextView textView = binding.textHome;
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        textHome = root.findViewById(R.id.text_home);
        textHome.setText(Activity2.insultString);

        return root;
    }
       

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}