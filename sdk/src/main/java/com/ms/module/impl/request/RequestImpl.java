package com.ms.module.impl.request;

import android.graphics.Bitmap;

import com.ms.module.impl.httpurlconnection.HttpURLConnectionUtils;
import com.ms.module.supers.client.Modules;
import com.ms.module.supers.inter.common.ICallBack;
import com.ms.module.supers.inter.net.IRequestAdapter;
import com.ms.module.supers.inter.net.IRequestCallBack;
import com.ms.module.supers.inter.net.Response;

import java.util.Map;

public class RequestImpl extends IRequestAdapter {
    public RequestImpl() {
    }

    @Override
    public Response get(Map<String, String> headers, String url) {
        return HttpURLConnectionUtils.doGet(headers, url);
    }

    @Override
    public void get(Map<String, String> headers, String url, IRequestCallBack callBack) {


        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.doGet(headers, url);
                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onException(response.throwable);
                    } else {
                        callBack.onSuccess(response);
                    }
                }
            }
        });


        super.get(headers, url, callBack);
    }

    @Override
    public Response post(Map<String, String> headers, String url, Map<String, String> params) {
        return HttpURLConnectionUtils.doPost(headers, url, params);
    }

    @Override
    public void post(Map<String, String> headers, String url, Map<String, String> params, IRequestCallBack callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.doPost(headers, url, params);

                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onException(response.throwable);
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
    public void requestBody(Map<String, String> headers, String url, String body, IRequestCallBack callBack) {
        Modules.getUtilsModule().getThreadPoolUtils().runSubThread(new Runnable() {
            @Override
            public void run() {
                Response response = HttpURLConnectionUtils.requestBody(headers, url, body);
                if (response.throwable != null) {
                    if (callBack != null) {
                        callBack.onException(response.throwable);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onSuccess(response);
                    }
                }
            }
        });
        super.requestBody(headers, url, body, callBack);
    }

    @Override
    public Bitmap downloadImage(String s) {
        return super.downloadImage(s);
    }
}
