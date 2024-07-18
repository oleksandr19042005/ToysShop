package com.example.toysshop;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.widget.TextView;

import com.example.toysshop.ui.buy_toy.BuyToyFragment;

public class BuyToyFragmentTest {

    private BuyToyFragment buyToyFragment;

    @Before
    public void setUp() {
        buyToyFragment = new BuyToyFragment();
    }

    @Test
    public void testBuyToys_InvalidQuantity() {
        Toy toy = new Toy();
        toy.setPrice("10.00");
        TextView budget = Mockito.mock(TextView.class);
        buyToyFragment.setBudgetTextView(budget);

        buyToyFragment.buyToys(toy, 0);

        Mockito.verify(budget, Mockito.never()).setText(ArgumentMatchers.anyString());
    }

    @Test
    public void testBuyToys_ValidQuantity() {
        Toy toy = new Toy();
        toy.setPrice("10.00");
        TextView budget = Mockito.mock(TextView.class);
        buyToyFragment.setBudgetTextView(budget);

        buyToyFragment.buyToys(toy, 2);

        Mockito.verify(budget).setText("Cost: 20.00");
    }
}
