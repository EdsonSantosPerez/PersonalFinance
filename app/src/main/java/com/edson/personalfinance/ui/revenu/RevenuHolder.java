package com.edson.personalfinance.ui.revenu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.model.Depense;
import com.edson.personalfinance.sqlite.database.model.Revenu;

public class RevenuHolder extends RecyclerView.ViewHolder {

    private TextView txtRowDate;
    private TextView txtRowCategory;
    private TextView txtRowDescription;
    private TextView txtRowValue;

    public RevenuHolder(View itemView) {
        super(itemView);
        txtRowDate = itemView.findViewById(R.id.txtRowDate);
        txtRowCategory = itemView.findViewById(R.id.txtRowCategory);
        txtRowDescription = itemView.findViewById(R.id.txtRowDescription);
        txtRowValue = itemView.findViewById(R.id.txtRowValue);
        txtRowValue.setTextColor(Color.parseColor("#00FF00"));
    }

    public void setDetails(Revenu revenu) {
        txtRowDate.setText(revenu.getDateTime());
        txtRowCategory.setText(revenu.getCategory());
        txtRowDescription.setText(revenu.getDescription());
        txtRowValue.setText(revenu.getValue() + " €");
    }

}
