package sp.anyconnectremote;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize any resources or perform any setup operations here
        Log.d("CustomApplication", "Application onCreate() called");

        MMKV.initialize(this);

//        init
        Global global = new Global();
        Static.globalData = global;

        global.mainApplication = this;

        global.mViewModel = new ViewModelProvider.AndroidViewModelFactory((Application) this.getApplicationContext()).create(MainViewModel.class);
        global.logManager = new LogManager();

        //بازیابی
        global.mViewModel.retrieveLogData();
        global.mViewModel.retrieveServiceStart();

        global.isCiscoInstalled = isAppInstalled(Static.globalData.ciscoPackageName);
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
