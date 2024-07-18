package com.example.toysshop.ui.add_toy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.toysshop.R;
import com.example.toysshop.database.ConSQL;
import com.example.toysshop.databinding.FragmentAddToyBinding;

import java.sql.Connection;
import java.sql.Statement;

public class AddToyFragment extends Fragment {

    private FragmentAddToyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddToyViewModel supportViewModel =
                new ViewModelProvider(this).get(AddToyViewModel.class);

        binding = FragmentAddToyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView toyName = binding.inputToyName;
        TextView toyPrice = binding.inputToyPrice;

        Spinner spinnerType = binding.spinnerForType;
        Spinner spinnerAge = binding.spinnerForAge;
        Spinner spinnerSize = binding.spinnerForSize;
        Button addToy = binding.buttonAddToy;
        Button clearAll = binding.btnClearInfAboutToy;

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getContext(),
                R.array.type_toys, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(getContext(),
                R.array.Age_group_toys, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(getContext(),
                R.array.size_toys, android.R.layout.simple_spinner_item);
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapterSize);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(parent.getContext(), "Please select a valid option", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        };

        spinnerType.setOnItemSelectedListener(itemSelectedListener);
        spinnerAge.setOnItemSelectedListener(itemSelectedListener);
        spinnerSize.setOnItemSelectedListener(itemSelectedListener);

        addToy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerType.getSelectedItemPosition() == 0 ||
                        spinnerAge.getSelectedItemPosition() == 0 ||
                        spinnerSize.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Please complete all selections", Toast.LENGTH_SHORT).show();
                } else {
                    String name = toyName.getText().toString();
                    String price = toyPrice.getText().toString();
                    String type = spinnerType.getSelectedItem().toString();
                    String ageForUse = spinnerAge.getSelectedItem().toString();
                    String size = spinnerSize.getSelectedItem().toString();

                    Connection connection;
                    ConSQL c = new ConSQL();
                    connection = c.conclass();
                    if (connection != null) {
                        try {

                            String sqlStatement = "INSERT INTO Toys (name, material, ageForUse, size, price) VALUES ('"+ name + "', '" + type + "', '" + ageForUse + "', '" + size + "', " + price + ");";
                            Statement smt = connection.createStatement();
                            smt.executeUpdate(sqlStatement);
                            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                            toyName.setText("");
                            toyPrice.setText("");
                            spinnerType.setSelection(0);
                            spinnerAge.setSelection(0);
                            spinnerSize.setSelection(0);
                            connection.close();
                        } catch (Exception e) {
                            Log.e("Result", e.getMessage());
                        }
                    }
                }
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toyName.setText("");
                toyPrice.setText("");
                spinnerType.setSelection(0);
                spinnerAge.setSelection(0);
                spinnerSize.setSelection(0);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
