package sp.anyconnectremote.data;

import androidx.annotation.NonNull;

// Static objects
public class Static {
    private static Global globalData;

    @NonNull
    public static Global getGlobalData() {
        if (globalData == null) {
            globalData = new Global();
        }
        return globalData;
    }
}