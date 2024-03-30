package sp.anyconnectremote.service.misc;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.app.NotificationManagerCompat;

import sp.anyconnectremote.AppConfig;
import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;

public abstract class AccessibilityServiceHelper extends AccessibilityService {
    private static final int NOTIFICATION_ID = 85;
    protected final Global data = Static.getGlobalData();

    @Override
    public void onCreate() {
        super.onCreate();

        data.getLogManager().saveLog("* Service created!");
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        // bug
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
////        info.packageNames = new String[]{data.getCiscoPackageName()};
//        setServiceInfo(info);

        data.getLogManager().saveLog("* Service connected! set package to: " + AppConfig.ciscoPackageName);
        // سرویس فعال شده است، نمایش یک Notification
        //showNotification();
    }

    @Override
    public void onInterrupt() {
        // متدی که در صورت وقوع وقفه در سرویس فراخوانده می‌شود
        data.getLogManager().saveLog("* Service Interrupt!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // سرویس غیرفعال شده است، حذف Notification
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
        data.getLogManager().saveLog("* Service destroyed!");
    }

    protected void traverseViews(AccessibilityNodeInfo node) {
        if (node == null) return;
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                // در اینجا می‌توانید دکمه‌ها و button ها را شناسایی و با آن‌ها کار کنید
                if (child.getClassName() != null && child.getClassName().toString().equals("android.widget.Button")) {
                    data.getLogManager().saveLog("* Button found: " + child);
                }
                traverseViews(child);
            }
        }
    }

    protected void performClickAction(AccessibilityNodeInfo node) {
        // انجام عملیات کلیک مورد نظر
        try {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            data.getLogManager().saveLog("ClickAction success!");
        } catch (Exception e) {
            data.getLogManager().saveLog("ClickAction error: " + e.getMessage());
        }
    }
}
