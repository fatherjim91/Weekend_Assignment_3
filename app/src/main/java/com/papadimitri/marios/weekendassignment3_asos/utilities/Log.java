package com.papadimitri.marios.weekendassignment3_asos.utilities;

import com.papadimitri.marios.weekendassignment3_asos.ApplicationASOS;

/**
 * Created by fatherjim on 08/04/2016.
 */
public class Log {
    static final boolean LOG = ApplicationASOS.ENABLE_LOGGING;

    public static void i(String tag, String string) {
        if (LOG) android.util.Log.i(tag,string);
    }

    public static void e(String tag, String string) {
        if (LOG) android.util.Log.e(tag, string);
    }

    public static void d(String tag, String string) {
        if (LOG) android.util.Log.d(tag, string);
    }

    public static void v(String tag, String string) {
        if (LOG) android.util.Log.v(tag, string);
    }

    public static void w(String tag, String string) {
        if (LOG) android.util.Log.w(tag, string);
    }
}