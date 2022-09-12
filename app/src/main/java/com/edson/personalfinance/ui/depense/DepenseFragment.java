package com.edson.personalfinance.ui.depense;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.DatabaseHelper;
import com.edson.personalfinance.sqlite.database.model.Depense;

import java.util.List;

public class DepenseFragment extends Fragment {

    private DepenseViewModel depenseViewModel;

    private DatabaseHelper db;
    private RecyclerView rclvDepenses;
    private DepenseAdapter adapter;
    private List<Depense> depenses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        depenseViewModel =
                new ViewModelProvider(this).get(DepenseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_depense, container, false);
        rclvDepenses = root.findViewById(R.id.rclvDepenses);
        rclvDepenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        createListData();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new DatabaseHelper(context);
    }

    private void createListData() {
        depenses = db.getAllDepenses();
        adapter = new DepenseAdapter(this.getContext(), depenses);
        rclvDepenses.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        rclvDepenses.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}