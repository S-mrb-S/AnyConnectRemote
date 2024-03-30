package sp.anyconnectremote.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.util.MmkvManager;

public class MainViewModel extends ViewModel {

    MmkvManager mmkvStorage = Static.getGlobalData().getMmkvStorage();

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
            logData.setValue(mmkvStorage.getLogValue());
    }

    public void saveLogData(@NonNull String newVal) {
        mmkvStorage.setLogValue(newVal);
        logData.setValue(newVal);
    }

    public void cleanLogData() {
        mmkvStorage.cleanLogValue();
        logData.setValue(Static.getGlobalData().getDefaultLogString());
    }

    // service boolean
    private final MutableLiveData<Boolean> isServiceStart = new MutableLiveData<>();

    public LiveData<Boolean> getServiceStart() {
        return isServiceStart;
    }

    public void retrieveServiceStart() {
        if (getIsServiceStart() == null)
            isServiceStart.setValue(mmkvStorage.getIsServiceValue());
    }

    public Boolean getIsServiceStart() {
        return isServiceStart.getValue();
    }

    public void saveServiceStart(@NonNull Boolean bool) {
        mmkvStorage.setIsServiceValue(bool);
        isServiceStart.setValue(bool);
    }
}