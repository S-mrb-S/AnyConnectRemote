package sp.anyconnectremote.util;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import sp.anyconnectremote.data.Static;

// by Mehrab
public class MmkvManager {

    @NonNull
    private final MMKV kv = MMKV.defaultMMKV();

    private final String logKey = "log";
    private final String isServiceKey = "isServiceStart";
    @NonNull
    private String logValue;
    @NonNull
    private Boolean isServiceValue;

    public MmkvManager() {
        isServiceValue = kv.decodeBool(isServiceKey, false);

        try {
            String newValue = kv.decodeString(logKey, Static.getGlobalData().getDefaultLogString());
            assert newValue != null;
            logValue = newValue;
        } catch (NullPointerException | AssertionError e) {
            logValue = Static.getGlobalData().getDefaultLogString();
        }
    }
//    synchronized

    @NonNull
    public synchronized String getLogValue() {
        return logValue;
    }

    public void setLogValue(@NonNull String logValue) {
        kv.encode(logKey, logValue);
        this.logValue = logValue;
    }

    public void cleanLogValue() {
        kv.encode(logKey, "");
        this.logValue = "";

        Static.getGlobalData().showToast("CLEANED");
    }

    @NonNull
    public synchronized Boolean getIsServiceValue() {
        return isServiceValue;
    }

    public void setIsServiceValue(@NonNull Boolean isServiceValue) {
        kv.encode(isServiceKey, isServiceValue);
        this.isServiceValue = isServiceValue;
    }
}