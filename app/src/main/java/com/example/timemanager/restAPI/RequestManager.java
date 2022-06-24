package com.example.timemanager.restAPI;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    public interface CallInsulAPI {
        @GET("generate_insult.php")
        Call<InsultModel> callInsult(
                @Query("language") String language,
                @Query("type") String type
                );
    }

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://evilinsult.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getInsult(OnFetchDataListener<InsultModel> onFetchDataListener, String language,String type)
    {
        CallInsulAPI callInsulAPI = retrofit.create(CallInsulAPI.class);
        Call<InsultModel> call = callInsulAPI.callInsult(language,type);
        try {
            call.enqueue(new Callback<InsultModel>() {
                @Override
                public void onResponse(Call<InsultModel> call, Response<InsultModel> response) {
                 if(!response.isSuccessful()){
                     Toast.makeText(context,"Error",Toast.LENGTH_LONG);
                 }
                 onFetchDataListener.onFetchData(response.body(),response.message());
                }

                @Override
                public void onFailure(Call<InsultModel> call, Throwable t) {
                    onFetchDataListener.onError("Request failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public RequestManager(Context context) {
        this.context = context;
    }
}

/*
public class RequestManager {
    public interface CallInsulAPI {
        @GET("top-headlines")
        Call<InsultModel> callInsult(
                @Query("country") String country,
                @Query("category") String category,
                @Query("apiKey") String api_key
        );
    }

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getInsult(OnFetchDataListener<InsultModel> onFetchDataListener,String category)
    {
        CallInsulAPI callInsulAPI = retrofit.create(CallInsulAPI.class);
        Call<InsultModel> call = callInsulAPI.callInsult("us",category, context.getString(R.string.api_key));
        try {
            call.enqueue(new Callback<InsultModel>() {
                @Override
                public void onResponse(Call<InsultModel> call, Response<InsultModel> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG);
                    }
                    onFetchDataListener.onFetchData(response.body(),response.message());
                }

                @Override
                public void onFailure(Call<InsultModel> call, Throwable t) {
                    onFetchDataListener.onError("Request failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public RequestManager(Context context) {
        this.context = context;
    }
}
*/
