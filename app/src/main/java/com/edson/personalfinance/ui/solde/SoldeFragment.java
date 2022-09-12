package com.edson.personalfinance.ui.solde;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.edson.personalfinance.R;
import com.edson.personalfinance.ui.depense.DepenseViewModel;

public class SoldeFragment extends Fragment {

    private SoldeViewModel soldeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        soldeViewModel =
                new ViewModelProvider(this).get(SoldeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_solde, container, false);
        final TextView textView = root.findViewById(R.id.text_solde);
        soldeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}