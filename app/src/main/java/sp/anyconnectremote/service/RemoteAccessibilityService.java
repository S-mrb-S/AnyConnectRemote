package sp.anyconnectremote.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import sp.anyconnectremote.service.misc.AccessibilityServiceHelper;

/**
 * by Mehrab
 */
public class RemoteAccessibilityService extends AccessibilityServiceHelper {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        executor.execute(() -> {

        data.getLogManager().saveLog("* event called!");
        if (data.isImportantErrorBoolean()) {
            data.getLogManager().saveLog("Service canceled");
            return;
        }

//        disableSelf();
//        AccessibilityNodeInfo source = event.getSource();
//        Log.d("TT", String.valueOf(event.getPackageName().equals(data.getCiscoPackageName())));
//        Log.d("TT2", String.valueOf(event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED));

        if (event.getPackageName() != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                if (rootNode != null) {
                    traverseViews(rootNode);
                } else {
                    data.getLogManager().saveLog("* no root: " + eventType);
                }
            } else {
                data.getLogManager().saveLog("* else: " + eventType);
            }
        } else {
            data.getLogManager().saveLog("* no package found!");
        }

//        });
//        executor.shutdown();


//        if (source != null) {
//            Log.d("if", "Not null!");
//            List<AccessibilityNodeInfo> settingsButtons = source.findAccessibilityNodeInfosByText("OK");
//            Log.d("On", "tt! " + settingsButtons);
//
//            if (settingsButtons != null && !settingsButtons.isEmpty()) {
//                AccessibilityNodeInfo settingsButton = settingsButtons.get(0);
//                if (settingsButton != null) {
//                    performClickAction(settingsButton);
//                    Log.d("On", "Peyda shd!!");
//                } else {
//                    Log.d("On", "Peyda nshd 2!");
//                }
//            } else {
//                Log.d("On", "Peyda nshd!");
//            }
//            // اینجا می‌توانید منطق خود را بر اساس رویدادهای دریافتی پیاده‌سازی کنید
//            // مثال: چک کردن آیا عنصری با شناسه خاص کلیک شده است و اگر اینطور بود، عملیات مورد نظر را انجام دهید
////            if ("com.android.chrome:id/settings_button".equals(source.getViewIdResourceName())) {
////                // انجام عملیات مورد نظر، مثلاً کلیک کردن روی دکمه تنظیمات
//////                performClickAction(source);
////
////            } else {
////                Log.d("On", "False!");
////            }
//        } else {
//            Log.d("if", "False!");
//        }
    }
}