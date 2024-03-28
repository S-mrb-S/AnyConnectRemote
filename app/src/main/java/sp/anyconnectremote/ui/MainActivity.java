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
import sp.anyconnectremote.databinding.ActivityMainBinding;
import sp.anyconnectremote.service.RemoteAccessibilityService;
import sp.anyconnectremote.ui.misc.BaseActivity;
import sp.anyconnectremote.util.LogManager;

public class MainActivity extends BaseActivity {

    private boolean isServiceConnect = false;
    private ActivityMainBinding binding;
    private String isServiceConnectText = "";

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                isServiceConnect = accessibilityManager.isEnabled();
            } catch (Exception e) {
                LogManager.logCat("[0] Error finding setting, default accessibility to not found: "
                        + e.getMessage());
            }
        } else {
            isServiceConnect = underMApi();
        }

        if (isServiceConnect) {
            isServiceConnectText = getResources().getString(R.string.service_active);
        } else {
            isServiceConnectText = getResources().getString(R.string.service_not_active);
        }

        binding.isConnectService.setText(isServiceConnectText);
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
                showToast("Received value: " + value);
            }
        });

        isServiceConnectText = getResources().getString(R.string.default_none);

        binding.isConnectServiceButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            } catch (Exception e) {
                LogManager.logCat("[3] Error finding setting, default accessibility to not found: "
                        + e.getMessage());
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
            LogManager.logCat("[1] Error finding setting, default accessibility to not found: "
                    + e.getMessage());
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
            LogManager.logCat("[2] Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }

        return isConnectService;
    }
}