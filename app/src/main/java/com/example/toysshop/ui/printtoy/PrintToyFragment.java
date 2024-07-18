package com.example.toysshop.ui.printtoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toysshop.FileLogger;
import com.example.toysshop.R;
import com.example.toysshop.Toy;
import com.example.toysshop.ToyAdapter;
import com.example.toysshop.database.ConSQL;
import com.example.toysshop.databinding.FragmentPrintToyBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PrintToyFragment extends Fragment implements ToyAdapter.OnItemDeleteListener {
    private FragmentPrintToyBinding binding;
    private RecyclerView recyclerView;
    private ToyAdapter adapter;
    private List<Toy> toyList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PrintToyViewModel printViewModel =
                new ViewModelProvider(this).get(PrintToyViewModel.class);

        binding = FragmentPrintToyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toyList = new ArrayList<>();
        adapter = new ToyAdapter(toyList, this);
        recyclerView.setAdapter(adapter);

        Button btnSearch = binding.search;
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToyData();
            }
        });

        binding.spinnerForSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getToyData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Нічого не робити
            }
        });

        getToyData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getToyData() {
        try {
            ConSQL connectionClass = new ConSQL();
            Connection con = connectionClass.conclass();
            Spinner filtres = binding.spinnerForSort;
            String value = filtres.getSelectedItem().toString();
            TextView searchName = binding.searchToyName;
            String searchNameString = searchName.getText().toString().trim();
            String query;
            boolean hasSearchName = !searchNameString.isEmpty();

            if (con != null) {


                if (value.equals("--Click for sort--")) {
                    if (hasSearchName) {
                        query = "SELECT * FROM Toys WHERE name = ?";
                    } else {
                        query = "SELECT * FROM Toys";
                    }
                } else {
                    if (hasSearchName) {
                        query = "SELECT * FROM Toys WHERE name = ? ORDER BY " + value.toLowerCase() + " ASC";
                    } else {
                        query = "SELECT * FROM Toys ORDER BY " + value.toLowerCase() + " ASC";
                    }
                }

                PreparedStatement stmt = con.prepareStatement(query);
                if (hasSearchName) {
                    stmt.setString(1, searchNameString);
                }

                ResultSet rs = stmt.executeQuery();
                toyList.clear();
                while (rs.next()) {
                    Toy toy = new Toy();
                    toy.setName(rs.getString("name"));
                    toy.setMaterial(rs.getString("material"));
                    toy.setAgeForUse(rs.getString("ageForUse"));
                    toy.setSize(rs.getString("size"));
                    toy.setPrice(rs.getString("price"));

                    toyList.add(toy);
                }
                adapter.notifyDataSetChanged();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemDelete(Toy toy) {
        try {
            ConSQL connectionClass = new ConSQL();
            if (connectionClass.deleteToy(toy.getName())) {
                toyList.remove(toy);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Toy deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to delete toy", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "An error occurred while deleting toy", Toast.LENGTH_SHORT).show();
        }
    }

}