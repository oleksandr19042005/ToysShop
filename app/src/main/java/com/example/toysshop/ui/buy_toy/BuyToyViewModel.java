package com.example.toysshop.ui.buy_toy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class BuyToyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BuyToyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery exit");
    }

    public LiveData<String> getText() {
        return mText;
    }
}