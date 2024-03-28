package sp.anyconnectremote.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.service.util.MainHelper;

/**
 * by Mehrab
 */
public class RemoteAccessibilityService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();

        Global.logManager.saveLog("service created!");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        Log.d("TT", String.valueOf(event.getPackageName().equals("com.cisco.anyconnect.vpn.android.avf")));
        Log.d("TT2", String.valueOf(event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED));

        if (source != null) {
            Log.d("if", "Not null!");
            List<AccessibilityNodeInfo> settingsButtons = source.findAccessibilityNodeInfosByText("OK");
            Log.d("On", "tt! " + settingsButtons);

            if (settingsButtons != null && !settingsButtons.isEmpty()) {
                AccessibilityNodeInfo settingsButton = settingsButtons.get(0);
                if (settingsButton != null) {
                    MainHelper.performClickAction(settingsButton);
                    Log.d("On", "Peyda shd!!");
                } else {
                    Log.d("On", "Peyda nshd 2!");
                }
            } else {
                Log.d("On", "Peyda nshd!");
            }
            // اینجا می‌توانید منطق خود را بر اساس رویدادهای دریافتی پیاده‌سازی کنید
            // مثال: چک کردن آیا عنصری با شناسه خاص کلیک شده است و اگر اینطور بود، عملیات مورد نظر را انجام دهید
//            if ("com.android.chrome:id/settings_button".equals(source.getViewIdResourceName())) {
//                // انجام عملیات مورد نظر، مثلاً کلیک کردن روی دکمه تنظیمات
////                performClickAction(source);
//
//            } else {
//                Log.d("On", "False!");
//            }
        } else {
            Log.d("if", "False!");
        }
    }

    @Override
    public void onInterrupt() {
        // متدی که در صورت وقوع وقفه در سرویس فراخوانده می‌شود
        Log.d("On", "Interput");

    }


}


