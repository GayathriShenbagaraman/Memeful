package com.memeful.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.memeful.BuildConfig;
import com.memeful.Global;
import com.memeful.callbacks.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WebService {

    private final String endPointUrl;

    private final Global.RequestMethod requestMethod;

    private final  Global.RequestType requestType;

    final ResponseCallBack mCallback;

    final HashMap<String, String> stringParams;

    final JSONObject jsonParam;

    public WebService(String endPointUrl, Global.RequestMethod requestMethod,
                      Global.RequestType requestType, ResponseCallBack mCallback, HashMap<String, String> stringParams, JSONObject jsonParam)

    {
        this.endPointUrl = endPointUrl;

        this.requestMethod = requestMethod;

        this.requestType = requestType;

        this.mCallback = mCallback;

        this.stringParams = stringParams;

        this.jsonParam = jsonParam;

    }

    public void sendRequest(Context context)
    {
        Context mContext;

        mContext = context;

        if (requestMethod.equals(Global.RequestMethod.GET) && requestType.equals(Global.RequestType.STRING_REQUEST))
        {
            StringRequest request = new StringRequest(Request.Method.GET, endPointUrl, onSuccess, onError) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Client-ID "+ BuildConfig.CLIENT_ID);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            WebRequestQueue.getInstance(mContext).addToRequestQueue(request);
        }

    }

    private final Response.Listener<JSONObject> onSuccessJson = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.i("PostActivity", response.toString());
            if(WebService.this.mCallback != null) {
                WebService.this.mCallback.onSuccess(response.toString());
            }
        }
    };

    private final Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            if(WebService.this.mCallback != null) {
                WebService.this.mCallback.onSuccess(response);
            }
        }
    };

    private final Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(WebService.this.mCallback != null) {
                WebService.this.mCallback.onFailure(error);
            }

            if (error == null || error.networkResponse == null) {

                Log.e("PostActivity", "Response value is null");
                return;
            }

            try {

                String data = new String(error.networkResponse.data,"UTF-8");
                Log.e("PostActivity", data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };


    public static class WebServiceBuilder
    {
        private String endPointUrl;

        private Global.RequestMethod requestMethod;

        private Global.RequestType requestType;

        private ResponseCallBack mCallback;

        private HashMap<String, String> stringParams;

        private JSONObject jsonParam;

        public WebServiceBuilder setEndPointUrl(String endPointUrl) {

            this.endPointUrl = endPointUrl;

            return this;
        }

        public WebServiceBuilder setRequestMethod(Global.RequestMethod requestMethod) {

            this.requestMethod = requestMethod;

            return this;
        }

        public  WebServiceBuilder setRequestType(Global.RequestType requestType)
        {
            this.requestType = requestType;
            return this;
        }

        public  WebServiceBuilder setCallback(ResponseCallBack callback)
        {
            this.mCallback = callback;
            return this;
        }

        public WebServiceBuilder setStringParams(HashMap<String, String> stringParams)
        {
            this.stringParams = stringParams;

            return this;
        }

        public WebServiceBuilder setJsonParams(JSONObject jsonParams)
        {
            this.jsonParam = jsonParams;

            return this;
        }

        public WebService build()

        {
            return new WebService(endPointUrl, requestMethod, requestType,
                    mCallback, stringParams, jsonParam);
        }

    }


}