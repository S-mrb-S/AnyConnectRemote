package sp.anyconnectremote;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.util.UncaughtExceptionHandler;

/*
March 28, 2024
 */
public class MainApplication extends Application {
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
            } catch (Exception e) {
                Toast.makeText(this, "Error found!", Toast.LENGTH_SHORT).show();
                data.setImportantErrorBoolean(true);
            }

            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

            //بازیابی
            data.getmViewModel().retrieveLogData();
            data.getmViewModel().retrieveServiceStart();

            data.setCiscoInstalled(isAppInstalled(data.getCiscoPackageName()));
        } catch (Exception e) {
            Log.d("MainApplication", "ERROR: " + e);
            Toast.makeText(this, "Error found!", Toast.LENGTH_SHORT).show();
            data.setImportantErrorBoolean(true);
        }
    }

    private boolean isAppInstalled(String packageName) {
        PackageManager packageManager = this.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Toast.makeText(this, "Warning! your memory is low!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Cleanup or perform final operations here before the application terminates
        Log.d("CustomApplication", "Application onTerminate() called");
    }
}
