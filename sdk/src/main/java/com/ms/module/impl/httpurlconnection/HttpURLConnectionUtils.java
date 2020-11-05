package com.ms.module.impl.httpurlconnection;


import com.ms.module.supers.client.Modules;
import com.ms.module.supers.inter.net.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpURLConnectionUtils {


    public static Response doGet(Map<String, String> headers, String url) {

        Response response = new Response();
        HttpURLConnection connection = null;
        try {
            //发起网络请求
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");//请求方式
            int connectTimeout = Modules.getRequestSettingModule().getConnectTimeout();
            int readTimeout = Modules.getRequestSettingModule().getReadTimeout();

            connection.setConnectTimeout(connectTimeout);//连接最大时间
            connection.setReadTimeout(readTimeout);//读取最大时间
            if (headers != null) {
                for (String k : headers.keySet()) {
                    if (null != k && !"".equals(k)) {
                        connection.setRequestProperty(k, headers.get(k));
                    }
                }
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            response.code = responseCode;
            String responseMessage = connection.getResponseMessage();
            response.message = responseMessage;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = null;
                StringBuffer resultString = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    resultString.append(str);
                }
                response.body = resultString.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.throwable = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    public static Response doPost(Map<String, String> headers, String url, Map<String, String> params) {
        Response response = new Response();
        HttpURLConnection connection = null;
        DataOutputStream dataOutputStream = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            int connectTimeout = Modules.getRequestSettingModule().getConnectTimeout();
            int readTimeout = Modules.getRequestSettingModule().getReadTimeout();
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            if (headers != null) {
                for (String k : headers.keySet()) {
                    if (null != k && !"".equals(k)) {
                        connection.setRequestProperty(k, headers.get(k));
                    }
                }
            }
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            if (params != null) {
                dataOutputStream = new DataOutputStream(connection
                        .getOutputStream());
                for (String it : params.keySet()) {
                    String content = it + "=" + URLEncoder.encode(params.get(it), "utf-8");
                    dataOutputStream.writeBytes(content);
                }
            }
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            response.code = responseCode;
            response.message = responseMessage;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String valueString = null;
                StringBuffer bufferResult = new StringBuffer();
                while ((valueString = bufferedReader.readLine()) != null) {
                    bufferResult.append(valueString);
                }
                response.body = bufferResult.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.throwable = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }


    public static Response requestBody(Map<String, String> headers, String url, String body) {
        Response response = new Response();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        PrintWriter out = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();//发起网络请求
            connection.setRequestMethod("POST");//请求方式
            int connectTimeout = Modules.getRequestSettingModule().getConnectTimeout();
            int readTimeout = Modules.getRequestSettingModule().getReadTimeout();

            //连接最大时间
            connection.setConnectTimeout(connectTimeout);
            //读取最大时间
            connection.setReadTimeout(readTimeout);
            if (headers != null) {
                for (String k : headers.keySet()) {
                    if (null != k && !"".equals(k)) {
                        connection.setRequestProperty(k, headers.get(k));
                    }
                }
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            out = new PrintWriter(connection.getOutputStream());
            out.write(body);
            out.flush();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));//写入reader
                StringBuilder resultString = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    resultString.append(str);
                }
                response.body = resultString.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.throwable = e;
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (reader != null) {
                    reader.close();
                }
                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}