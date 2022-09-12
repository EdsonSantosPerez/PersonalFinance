package com.edson.personalfinance.ui.solde;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SoldeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SoldeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is solde fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}