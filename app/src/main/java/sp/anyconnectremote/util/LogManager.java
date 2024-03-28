package sp.anyconnectremote.util;

import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sp.anyconnectremote.BuildConfig;

public class LogManager {
    private static final MMKV kv = MMKV.defaultMMKV();

    public static void saveLog(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());
            String resultLog = getLog() +
                    "\n" +
                    "Log[" +
                    currentDateAndTime +
                    "]: " +
                    str;

            kv.encode("log", resultLog);

            logCat("Log saved: " + str);
        }catch (Exception e){
            logCat("Error in saving: " + e, true);
        }
    }

    public static String getLog(){
        try{
            return kv.decodeString("log", "** ** ** **");
        }catch (Exception e){
            logCat("Error in getting: " + e, true);
            return "";
        }
    }

    public static void logCat(String str) {
        logCat(str, false); // Default value for e is false
    }
    public static void logCat(String str, boolean logType){
        if (BuildConfig.DEBUG) {
            if(logType){
                Log.e("LogManager", str);
            } else Log.d("LogManager", str);
            // Perform additional debug-only operations if needed
        }
    }
}
