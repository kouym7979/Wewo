package com.example.finalp.ui.settings;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;

        import com.example.finalp.R;
        import com.example.finalp.modifyPassword;

public class SettingsFragment extends Fragment{

    //private SettingsViewModel settingsViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }
}