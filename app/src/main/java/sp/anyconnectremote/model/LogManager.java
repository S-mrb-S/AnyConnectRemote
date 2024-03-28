package sp.anyconnectremote.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sp.anyconnectremote.BuildConfig;
import sp.anyconnectremote.data.Static;

public class LogManager {

    public void saveLog(String str) {
        // No thread work in ViewModel
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.execute(() -> {
//        });
//        executor.shutdown();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());

            String resultLog = Static.globalData.mViewModel.getCurrentTextLogData() +
                    "\n" +
                    "Log[" +
                    currentDateAndTime +
                    "]: " +
                    str;

            Static.globalData.mViewModel.saveLogData(resultLog);

            logCat("Log saved: " + str);
        } catch (Exception e) {
            logCat("Error in saving: " + e, true);
        }
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
