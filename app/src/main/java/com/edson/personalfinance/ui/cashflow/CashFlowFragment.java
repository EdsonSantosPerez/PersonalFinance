package com.edson.personalfinance.ui.cashflow;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.DatabaseHelper;
import com.edson.personalfinance.sqlite.database.model.Depense;
import com.edson.personalfinance.sqlite.database.model.Revenu;
import com.edson.personalfinance.ui.depense.DepenseViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashFlowFragment extends Fragment {

    DatabaseHelper db;
    Depense depense;
    Revenu revenu;

    private CashFlowViewModel cashFlowViewModel;
    RadioGroup radioGroup;
    RadioButton rdBtnSelected;
    RadioButton rdBtnRevenu;
    RadioButton rdBtnDepense;
    EditText txtValue;
    Spinner spnCategory;
    EditText txtDescription;
    Button btnTransaction;

    List<String> spinnerArray;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cashFlowViewModel = new ViewModelProvider(this).get(CashFlowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cashflow, container, false);
        radioGroup = root.findViewById(R.id.radioGroup);
        rdBtnRevenu = root.findViewById(R.id.rdBtnRevenu);
        rdBtnDepense = root.findViewById(R.id.rdBtnDepense);
        txtValue = root.findViewById(R.id.txtValue);
        spnCategory = root.findViewById(R.id.spnCategory);
        txtDescription = root.findViewById(R.id.txtDescription);
        btnTransaction = root.findViewById(R.id.btnTransaction);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rdBtnSelected = root.findViewById(checkedId);
                initializeSpinner(rdBtnSelected.getText().toString());
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTransaction();
            }
        });
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new DatabaseHelper(context);
    }

    private void initializeSpinner(String text) {
        spinnerArray = new ArrayList<>();

        if (text.equals("Dépense") && text != null) {
            spinnerArray.add("Logement");
            spinnerArray.add("Alimentation");
            spinnerArray.add("Autres nécessités");
            spinnerArray.add("Loisirs");
            spinnerArray.add("Autres");
        } else if (text.equals("Revenu") && text != null) {
            spinnerArray.add("Principal");
            spinnerArray.add("Secondaire");
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        spnCategory.setAdapter(adapter);
    }

    private void doTransaction() {
        String pattern = "yyyy-MM-dd HH:mm:ss";//dd-M-yyyy hh:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date now = new Date();
        String date = sdf.format(now);//YYYY-MM-DD hh: mm: ss.nnn
        String res = "";
        if (rdBtnRevenu.isChecked()) {
            revenu = new Revenu();
            revenu.setCategory(spnCategory.getSelectedItem().toString());
            revenu.setDescription(txtDescription.getText().toString());
            revenu.setValue(Double.parseDouble(txtValue.getText().toString()));
            revenu.setDateTime(date);
//            Toast.makeText(this.getContext(), "Date " + revenu.getDateTime() + "\nCategory " + revenu.getCategory() + "\n Description " + revenu.getDescription() + "\n Value " + revenu.getValue(), Toast.LENGTH_SHORT).show();
            res = db.insertRevenu(revenu);
        } else if (rdBtnDepense.isChecked()) {
            depense = new Depense();
//            Toast.makeText(this.getContext(), spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            depense.setCategory(spnCategory.getSelectedItem().toString());
            depense.setDescription(txtDescription.getText().toString());
            depense.setValue(Double.parseDouble(txtValue.getText().toString()));
            depense.setDateTime(date);
            res = db.insertDepense(depense);
        } else {
                res = "Assurez-vous que tous les champs soient complets !";
        }
        Toast.makeText(this.getContext(), res, Toast.LENGTH_SHORT).show();
    }

}