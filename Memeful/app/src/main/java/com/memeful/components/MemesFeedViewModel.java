package com.memeful.components;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.memeful.BuildConfig;
import com.memeful.Global;
import com.memeful.callbacks.ResponseCallBack;
import com.memeful.manager.WebService;
import com.memeful.model.GalleryResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemesFeedViewModel extends AndroidViewModel implements ResponseCallBack {
    private MutableLiveData<List<GalleryResponseModel>> posts;
    private List<GalleryResponseModel> memes = new ArrayList<>();

    private MutableLiveData<Integer> itemNumber;
    private Gson mGson;

    private Context mContext;
    private WebService webService;
    private static int PAGE_SIZE =30;

    public MemesFeedViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
        mGson = new GsonBuilder().create();
    }

    public MutableLiveData<List<GalleryResponseModel>> getPosts() {
        return posts;
    }

    //Load initial data if there is no post or increment page and load data
    public void loadData() {
        if(posts==null){
            posts = new MediatorLiveData<>();
            itemNumber = new MediatorLiveData<>();
            itemNumber.setValue(0);
            webService = new WebService.WebServiceBuilder()
                    .setEndPointUrl(BuildConfig.BASE_URL+ itemNumber.getValue() +"?showViral=true&mature=true&album_previews=true")
                    .setRequestMethod(Global.RequestMethod.GET)
                    .setRequestType(Global.RequestType.STRING_REQUEST)
                    .setCallback(this)
                    .build();

            webService.sendRequest(mContext);
        } else {
            itemNumber.setValue(itemNumber.getValue() + 1);
            webService = new WebService.WebServiceBuilder()
                    .setEndPointUrl(BuildConfig.BASE_URL+ itemNumber.getValue() +"?showViral=true&mature=true&album_previews=true")
                    .setRequestMethod(Global.RequestMethod.GET)
                    .setRequestType(Global.RequestType.STRING_REQUEST)
                    .setCallback(this)
                    .build();

            webService.sendRequest(mContext);
        }
    }

    @Override
    public void onSuccess(String response) {
        Log.d("API", response);

        try {
            JsonParser parser = new JsonParser();
            JsonObject data = parser.parse(response).getAsJsonObject();
            JsonArray array = data.getAsJsonArray("data");

            List<GalleryResponseModel> memeList = Arrays.asList(mGson.fromJson(array, GalleryResponseModel[].class));
            memes.addAll(memeList);
            posts.setValue(memes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(VolleyError error) {
        Log.d("API", error.getMessage());
    }
}