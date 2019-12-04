package com.example.finalp.ui.settings;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;

        import com.example.finalp.FAQActivity;
        import com.example.finalp.NoticeActivity;
        import com.example.finalp.R;

public class SettingsFragment extends Fragment{

    //private SettingsViewModel settingsViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        startActivity(new Intent(getActivity(), FAQActivity.class));
        return view;
    }

}