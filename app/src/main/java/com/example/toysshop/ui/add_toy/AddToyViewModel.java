package com.example.toysshop.ui.add_toy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddToyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddToyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery exit");
    }

    public LiveData<String> getText() {
        return mText;
    }
}