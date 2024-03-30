package sp.anyconnectremote.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sp.anyconnectremote.BuildConfig;
import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;

public class LogManager {
    private final Global data = Static.getGlobalData();

    public void saveLog(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());

            String resultLog = data.getmViewModel().getCurrentTextLogData() +
                    "\n" +
                    "Log[" +
                    currentDateAndTime +
                    "]: " +
                    str;

            data.getmViewModel().saveLogData(resultLog);

            logCat("Log saved: " + str);
        } catch (Exception e) {
            logCat("Error in saving: " + e, true);
        }
    }

    public void cleanLog() {
        data.getmViewModel().cleanLogData();
    }

    public void logCat(String str) {
        logCat(str, false); // Default value for e is false
    }

    public void logCat(String str, boolean logType) {
        if (BuildConfig.DEBUG) {
            if (logType) {
                Log.e("LogManager", str);
            } else Log.d("LogManager", str);
            // Perform additional debug-only operations if needed
        }
    }
}
