package sp.anyconnectremote;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize any resources or perform any setup operations here
        Log.d("CustomApplication", "Application onCreate() called");
        MMKV.initialize(this);
        Global.logManager = new LogManager();
        Global.mViewModel = new ViewModelProvider.AndroidViewModelFactory((Application) this.getApplicationContext()).create(MainViewModel.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Cleanup or perform final operations here before the application terminates
        Log.d("CustomApplication", "Application onTerminate() called");
    }
}
