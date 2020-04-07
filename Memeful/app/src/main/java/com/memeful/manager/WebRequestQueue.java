package com.memeful.manager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class WebRequestQueue {

    private static WebRequestQueue mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private WebRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized WebRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WebRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(StringRequest request)
    {
        mRequestQueue.add(request);
    }

    public void addToRequestQueue(JsonObjectRequest request)
    {
        mRequestQueue.add(request);
    }


    public void addToRequestQueue(JsonArrayRequest request)
    {
        mRequestQueue.add(request);
    }
}
