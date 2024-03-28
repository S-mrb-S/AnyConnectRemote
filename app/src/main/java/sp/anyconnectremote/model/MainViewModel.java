package sp.anyconnectremote.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Global;

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
        if (logData.getValue() == null)
            logData.setValue(kv.decodeString("log", Global.defaultLogString));
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
        if (isServiceStart.getValue() == null)
            isServiceStart.setValue(kv.decodeBool("isServiceStart", false));
    }

    public void saveServiceStart(Boolean bool) {
        kv.encode("isServiceStart", bool);
        isServiceStart.setValue(bool);
    }
}