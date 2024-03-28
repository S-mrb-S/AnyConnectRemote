package sp.anyconnectremote.util;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sp.anyconnectremote.data.Static;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, @NonNull Throwable e) {
        System.out.println("Caught an unhandled exception in thread: " + t.getName());
        if (e instanceof AssertionError) {
            Static.getGlobalData().getMainApplication();
            Toast.makeText(Static.getGlobalData().getMainApplication(), "Warning: AssertionError", Toast.LENGTH_LONG).show();
            Log.d("Thread Handler", "ERROR: " + e);
        } else {
            Static.getGlobalData().getMainApplication();
            Toast.makeText(Static.getGlobalData().getMainApplication(), "Warning! A problem was found in the application", Toast.LENGTH_LONG).show();
            Log.d("Thread Handler", "ERROR: " + e);
        }
    }
}