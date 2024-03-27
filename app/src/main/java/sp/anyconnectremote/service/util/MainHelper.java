package sp.anyconnectremote.service.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

public class MainHelper {

    public static void performClickAction(AccessibilityNodeInfo node) {
        // انجام عملیات کلیک مورد نظر
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        Log.d("On", "Clicked");
    }
}
