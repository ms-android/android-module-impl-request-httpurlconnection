package com.ms.module.impl.httpurlconnection;

import android.util.Log;

public class RequestLogUtils {

    public static boolean outFlag = false;


    public static void i(String tag, String log) {
        if (outFlag) {
            Log.i(tag, log);
        }
    }



    public static void d(String tag, String log) {
        if (outFlag) {
            Log.d(tag, log);
        }
    }

    public static void e(String tag, String log) {
        if (outFlag) {
            Log.e(tag, log);
        }
    }
}
