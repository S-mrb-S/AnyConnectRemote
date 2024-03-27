package sp.anyconnectremote;

import android.app.Application;
import android.util.Log;

import com.tencent.mmkv.MMKV;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize any resources or perform any setup operations here
        Log.d("CustomApplication", "Application onCreate() called");
        MMKV.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Cleanup or perform final operations here before the application terminates
        Log.d("CustomApplication", "Application onTerminate() called");
    }
}
