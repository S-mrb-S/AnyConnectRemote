package sp.anyconnectremote.data;

import android.app.Application;

import androidx.annotation.Nullable;

import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;
import sp.anyconnectremote.util.MmkvManager;

public class GlobalHelper {
    protected LogManager logManager;
    protected MainViewModel mViewModel;
    protected MmkvManager mmkvStorage;
    @Nullable
    protected Application mainApplication;
    protected boolean isImportantErrorBoolean = false;
}
