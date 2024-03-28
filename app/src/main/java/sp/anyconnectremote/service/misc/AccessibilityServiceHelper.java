package sp.anyconnectremote.service.misc;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import sp.anyconnectremote.R;
import sp.anyconnectremote.data.Static;

public abstract class AccessibilityServiceHelper extends AccessibilityService {
    private static final int NOTIFICATION_ID = 85;

    @Override
    public void onCreate() {
        super.onCreate();

        Static.globalData.logManager.saveLog("* Service created!");
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        Static.globalData.logManager.saveLog("* Service connected!");
        // سرویس فعال شده است، نمایش یک Notification
        showNotification();
    }

    @Override
    public void onInterrupt() {
        // متدی که در صورت وقوع وقفه در سرویس فراخوانده می‌شود
        Static.globalData.logManager.saveLog("* Service Interrupt!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // سرویس غیرفعال شده است، حذف Notification
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
        Static.globalData.logManager.saveLog("* Service destroyed!");
    }

    private void showNotification() {
        // برای SDK اندروید 8 (API level 26) و بالاتر
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Channel Name";
//            String description = "Channel Description";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                Static.globalData.logManager.saveLog("POST_NOTIFICATIONS is not granted!");
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle("Accessibility Service is active")
                    .setContentText("Your Accessibility Service is currently active.")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());
        } catch (Exception e) {
            Static.globalData.logManager.saveLog("Service notification not created!");
        }
    }

    protected void performClickAction(AccessibilityNodeInfo node) {
        // انجام عملیات کلیک مورد نظر
        try {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Static.globalData.logManager.saveLog("ClickAction success!");
        } catch (Exception e) {
            Static.globalData.logManager.saveLog("ClickAction error: " + e.getMessage());
        }
    }
}
