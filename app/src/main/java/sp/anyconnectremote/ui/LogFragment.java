package sp.anyconnectremote.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import sp.anyconnectremote.databinding.FragmentLogBinding;
import sp.anyconnectremote.model.MainViewModel;
import sp.anyconnectremote.ui.misc.BindingFragment;

/// by Mehrab
public class LogFragment extends BindingFragment<FragmentLogBinding> {

    private MainViewModel mViewModel;

    @Override
    protected FragmentLogBinding getViewBinding() {
        return FragmentLogBinding.inflate(getLayoutInflater());
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.retrieveTextData(); // بازیابی متن از MMKV
        mViewModel.getTextData().observe(getViewLifecycleOwner(), text -> {
            // زمانی که مقدار LiveData تغییر کند، این متد فراخوانی می‌شود
            // در اینجا مقدار TextView را بروز می‌کنیم
            binding.logText.setText(text);
        });
    }
}