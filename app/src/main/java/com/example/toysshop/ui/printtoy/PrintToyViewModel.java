package com.example.toysshop.ui.printtoy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class PrintToyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PrintToyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is support fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
