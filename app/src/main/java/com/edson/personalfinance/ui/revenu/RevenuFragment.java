package com.edson.personalfinance.ui.revenu;

import android.content.Context;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.DatabaseHelper;
import com.edson.personalfinance.sqlite.database.model.Revenu;
import com.edson.personalfinance.ui.depense.DepenseAdapter;

import java.util.List;

public class RevenuFragment extends Fragment {

    private RevenuViewModel revenuViewModel;

    private DatabaseHelper db;
    private RecyclerView rclvRevenus;
    private RevenuAdapter adapter;
    private List<Revenu> revenus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        revenuViewModel =
                new ViewModelProvider(this).get(RevenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_revenu, container, false);
        rclvRevenus = root.findViewById(R.id.rclvRevenus);
        rclvRevenus.setLayoutManager(new LinearLayoutManager(getActivity()));
        createListData();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new DatabaseHelper(context);
    }

    private void createListData() {
        revenus = db.getAllRevenus();
        adapter = new RevenuAdapter(this.getContext(), revenus);
        rclvRevenus.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        rclvRevenus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}