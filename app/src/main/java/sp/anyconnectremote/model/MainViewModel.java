package sp.anyconnectremote.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sp.anyconnectremote.util.LogManager;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> textData = new MutableLiveData<>();

    public LiveData<String> getTextData() {
        return textData;
    }

    public void retrieveTextData() {
        textData.setValue(LogManager.getLog());
    }
}