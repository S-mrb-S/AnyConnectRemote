package sp.anyconnectremote.ui.misc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;

public abstract class BindingFragment<T extends ViewBinding> extends Fragment {
    protected T binding;
    protected final Global data = Static.getGlobalData();

    protected abstract T getViewBinding();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding();
        return binding.getRoot();
    }
}
