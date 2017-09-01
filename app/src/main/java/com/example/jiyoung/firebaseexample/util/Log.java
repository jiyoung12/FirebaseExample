package com.example.jiyoung.firebaseexample.util;

/**
 * Created by jiyoung on 2017. 9. 1..
 */

public class Log {

    protected static boolean isShow = true;

    public static final String TAG = "FireBaseExample";

    public static void d(String tag, String msg) {
        if (isShow) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isShow) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isShow) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void d(String message) {
        if (isShow) {
            String tag = "";
            String temp = new Throwable().getStackTrace()[1].getClassName();
            if (temp != null) {
                int lastDotPos = temp.lastIndexOf(".");
                tag = temp.substring(lastDotPos + 1);
            }
            String methodName = new Throwable().getStackTrace()[1]
                    .getMethodName();
            int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

            String logText = "[" + tag + "] " + methodName + "()" + "["
                    + lineNumber + "]" + " >> " + message;
            Log.d(TAG, logText);
        }
    }

    public static void i(String message) {
        if (isShow) {
            String tag = "";
            String temp = new Throwable().getStackTrace()[1].getClassName();
            if (temp != null) {
                int lastDotPos = temp.lastIndexOf(".");
                tag = temp.substring(lastDotPos + 1);
            }
            String methodName = new Throwable().getStackTrace()[1]
                    .getMethodName();
            int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

            String logText = "[" + tag + "] " + methodName + "()" + "["
                    + lineNumber + "]" + " >> " + message;
            Log.i(TAG, logText);
        }
    }

    public static void e(String message) {
        if (isShow) {
            String tag = "";
            String temp = new Throwable().getStackTrace()[1].getClassName();
            if (temp != null) {
                int lastDotPos = temp.lastIndexOf(".");
                tag = temp.substring(lastDotPos + 1);
            }
            String methodName = new Throwable().getStackTrace()[1]
                    .getMethodName();
            int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

            String logText = "[" + tag + "] " + methodName + "()" + "["
                    + lineNumber + "]" + " >> " + message;
            Log.e(TAG, logText);
        }
    }
}