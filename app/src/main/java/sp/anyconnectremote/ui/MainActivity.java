package sp.anyconnectremote.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sp.anyconnectremote.R;
import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.databinding.ActivityMainBinding;
import sp.anyconnectremote.service.RemoteAccessibilityService;
import sp.anyconnectremote.ui.misc.BaseActivity;

public class MainActivity extends BaseActivity {
    private boolean isServiceConnect = false;
    private ActivityMainBinding binding;
    private final Global data = Static.getGlobalData();

    @Override
    protected void onResume() {
        super.onResume();
        data.showToast("Resume");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                isServiceConnect = accessibilityManager.isEnabled();
            } catch (Exception e) {
                data.getLogManager().logCat("[0] Error finding setting, default accessibility to not found: "
                        + e.getMessage(), true);
            }
        } else {
            isServiceConnect = underMApi();
        }

        String isServiceConnectText =
                isServiceConnect ? getResources().getString(R.string.service_active) :
                        getResources().getString(R.string.service_not_active);
        binding.isConnectService.setText(isServiceConnectText);

//         java17
        Global.CiscoInstallationStatus status = data.getCiscoInstallationStatus();
        boolean appInstalled = status.appInstalled();

        if (appInstalled) {
            boolean permissionsEnabled = status.permissionsEnabled();
            data.showToast("Welcome!");
            data.showToast(String.valueOf(permissionsEnabled));
        } else {
            System.out.println("Cisco Anyconnect not found!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        activityLauncher.setOnActivityResult(result -> {
            if (result.getResultCode() == RESULT_OK) {
                String value = result.getData() != null ? result.getData().getStringExtra("key") : null;
                data.showToast("Received value: " + value);
            }
        });

        binding.isConnectServiceButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            } catch (Exception e) {
                data.getLogManager().logCat("[3] Error finding setting, default accessibility to not found: "
                        + e.getMessage(), true);
            }
        });
    }

    private boolean underMApi() {
        int accessibilityEnabled = 0;
        boolean isConnectService = false;
        final String service = getPackageName() + "/" + RemoteAccessibilityService.class.getName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            data.getLogManager().logCat("[1] Error finding setting, default accessibility to not found: "
                    + e.getMessage(), true);
        }

        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        try {
            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue);
                    while (mStringColonSplitter.hasNext()) {
                        String accessibilityService = mStringColonSplitter.next();

                        if (accessibilityService.equalsIgnoreCase(service)) {
                            isConnectService = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            data.getLogManager().logCat("[2] Error finding setting, default accessibility to not found: "
                    + e.getMessage(), true);
        }

        return isConnectService;
    }
}