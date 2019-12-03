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
    EditText useremail;
    Button password_find;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        password_find = (Button)view.findViewById(R.id.buttonFind);
        useremail = (EditText)view.findViewById(R.id.editTextUserEmail);



        password_find.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ((modifyPassword)getActivity()).onClick(password_find);
            }
        });
        return view;
    }
}