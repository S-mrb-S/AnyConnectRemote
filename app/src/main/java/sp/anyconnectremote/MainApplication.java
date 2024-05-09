package sp.anyconnectremote;

import android.util.Log;
import android.widget.Toast;

import com.cisco.anyconnect.vpn.android.ui.helpers.GlobalAppHelpers;
import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.util.UncaughtExceptionHandler;

/*
March 28, 2024
 */
public class MainApplication extends GlobalAppHelpers {
    private Global data;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize any resources or perform any setup operations here
        Log.d("CustomApplication", "Application onCreate() called");

        // نیاز هست که تمام داده ها و لایو دیتا به صورت استاتیک باشند تا سرویس بتواند بدون اینکه هیچ اکتیویتی باز هست به همه چی دسترسی داشته باشد
        try {
            try {
                MMKV.initialize(this);
                Static.setGlobalData(this);
                data = Static.getGlobalData();
            } catch (AssertionError | Exception e) {
                Toast.makeText(this, "Error found!", Toast.LENGTH_SHORT).show();
                data.setImportantErrorBoolean(true);
            }

            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

            //بازیابی
            data.getmViewModel().retrieveLogData();
            data.getmViewModel().retrieveServiceStart();

        } catch (Exception e) {
            Log.d("MainApplication", "ERROR: " + e);
            Toast.makeText(this, "Error found!", Toast.LENGTH_SHORT).show();
            data.setImportantErrorBoolean(true);
        }
    }
}
