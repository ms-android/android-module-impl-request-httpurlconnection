package com.ms.module.impl.request;

import android.graphics.Bitmap;

import com.ms.module.impl.httpurlconnection.HttpURLConnectionUtils;
import com.ms.module.impl.httpurlconnection.RequestLogUtils;
import com.ms.module.supers.client.Modules;
import com.ms.module.supers.inter.request.IRequestAdapter;
import com.ms.module.supers.inter.request.RequestCallBack;
import com.ms.module.supers.inter.request.Response;

import java.io.IOException;
import java.util.Map;

public class RequestImpl extends IRequestAdapter {
    public RequestImpl() {


        boolean requestLogOut = Modules.getRequestSettingModule().getRequestLogOut();
        RequestLogUtils.outFlag = requestLogOut;


    }

    @Override
    public Response get(Map<String, String> headers, String url) {
        return HttpURLConnectionUtils.doGet(headers, url);
    }

    @Override
    public void get(Map<String, String> headers, String url, RequestCallBack callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.doGet(headers, url);
                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onFailure(response.throwable);
                    } else {
                        callBack.onSuccess(response);
                    }
                }
            }
        });
    }

    @Override
    public Response post(Map<String, String> headers, String url, Map<String, String> params) {
        return HttpURLConnectionUtils.doPost(headers, url, params);
    }

    @Override
    public void post(Map<String, String> headers, String url, Map<String, String> params, RequestCallBack callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.doPost(headers, url, params);

                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onFailure(response.throwable);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess(response);
                    }
                }
            }
        });
    }

    @Override
    public Response requestBody(Map<String, String> headers, String url, String body) {
        return HttpURLConnectionUtils.requestBody(headers, url, body);
    }

    @Override
    public void requestBody(Map<String, String> headers, String url, String body, RequestCallBack callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.requestBody(headers, url, body);
                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onFailure(response.throwable);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess(response);
                    }
                }
            }
        });
    }

    @Override
    public Bitmap downloadImage(String url) {
        return HttpURLConnectionUtils.downloadImage(url);
    }


    @Override
    public void downloadImage(String url, RequestCallBack<Bitmap, Throwable> callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = HttpURLConnectionUtils.downloadImage(url);
                if (bitmap != null) {
                    if (callBack != null) {
                        callBack.onSuccess(bitmap);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFailure(new IOException());
                    }
                }
            }
        });
    }
}
