package com.example.finalp.ui.license;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalp.R;

public class licenseFragment extends Fragment {

    private LicenseViewModel licenseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        licenseViewModel =
                ViewModelProviders.of(this).get(LicenseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_license, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        licenseViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}