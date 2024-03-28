package sp.anyconnectremote.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import sp.anyconnectremote.R;
import sp.anyconnectremote.data.Static;
import sp.anyconnectremote.databinding.FragmentLogBinding;
import sp.anyconnectremote.ui.misc.BindingFragment;

/// by Mehrab
public class LogFragment extends BindingFragment<FragmentLogBinding> {
    @Override
    protected FragmentLogBinding getViewBinding() {
        return FragmentLogBinding.inflate(getLayoutInflater());
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //LiveData
        Static.globalData.mViewModel.getLogData().observe(getViewLifecycleOwner(), text -> {
            binding.logText.setText(text);
        });

        Static.globalData.mViewModel.getServiceStart().observe(getViewLifecycleOwner(), isServiceConnect -> {
            String isServiceConnectText =
                    isServiceConnect ? getResources().getString(R.string.service_active) :
                            getResources().getString(R.string.service_not_active);

            binding.isConnectService.setText(isServiceConnectText);
        });
    }
}