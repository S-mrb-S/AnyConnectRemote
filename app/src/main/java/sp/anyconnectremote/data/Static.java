package sp.anyconnectremote.data;

import android.app.Application;

import androidx.annotation.NonNull;

// Static objects
public class Static {
    private static Global globalData;

    @NonNull
    public static Global getGlobalData() {
        assert globalData != null;
        return globalData;
    }

    public static void setGlobalData(Application context) {
        assert globalData == null;
        globalData = new Global(context);
    }
}