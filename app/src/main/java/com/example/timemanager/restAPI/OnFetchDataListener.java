package com.example.timemanager.restAPI;

public interface OnFetchDataListener<InsultModel> {
    void onFetchData(InsultModel insult,String message);
    void onError(String message);
}
