package com.memeful.callbacks;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ResponseCallBack {

void onSuccess(String response);;

void onFailure(VolleyError error);
}