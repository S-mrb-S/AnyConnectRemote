package sp.anyconnectremote.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Static;

public class MainViewModel extends ViewModel {
    public final MMKV kv = MMKV.defaultMMKV();

    // log list
    private final MutableLiveData<String> logData = new MutableLiveData<>();

    public LiveData<String> getLogData() {
        return logData;
    }

    public String getCurrentTextLogData() {
        return logData.getValue();
    }

    public void retrieveLogData() {
        if (getCurrentTextLogData() == null)
            logData.setValue(kv.decodeString("log", Static.globalData.defaultLogString));
    }

    public void saveLogData(String newVal) {
        kv.encode("log", newVal);
        logData.setValue(newVal);
    }

    // service boolean
    private final MutableLiveData<Boolean> isServiceStart = new MutableLiveData<>();

    public LiveData<Boolean> getServiceStart() {
        return isServiceStart;
    }

    public void retrieveServiceStart() {
        if (getIsServiceStart() == null)
            isServiceStart.setValue(kv.decodeBool("isServiceStart", false));
    }

    public Boolean getIsServiceStart() {
        return isServiceStart.getValue();
    }

    public void saveServiceStart(Boolean bool) {
        kv.encode("isServiceStart", bool);
        isServiceStart.setValue(bool);
    }
}