package com.example.toysshop.ui.buy_toy;



import android.content.Intent;
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

import com.example.toysshop.Basket;
import com.example.toysshop.R;
import com.example.toysshop.Toy;
import com.example.toysshop.ToyAdapterCustomer;
import com.example.toysshop.database.ConSQL;
import com.example.toysshop.databinding.FragmentBuyToyBinding;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BuyToyFragment extends Fragment implements ToyAdapterCustomer.buyToysAddToBasket {
    private FragmentBuyToyBinding binding;
    private RecyclerView recyclerView;
    private ToyAdapterCustomer adapter;
    private List<Toy> toyList, toyListBuy;
    private double budgetDouble = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuyToyViewModel buyToyViewModel =
                new ViewModelProvider(this).get(BuyToyViewModel.class);

        binding = FragmentBuyToyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toyList = new ArrayList<>();
        adapter = new ToyAdapterCustomer(toyList, this);
        recyclerView.setAdapter(adapter);

        toyListBuy = new ArrayList<>();
        Button btnBasket=binding.btnBasket;
        btnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireContext(), Basket.class);
                startActivity(intent);
            }
        });
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
                    toy.setToy_id(rs.getString("id"));
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
    public void buyToys(Toy toy, int quantity) {
        if (quantity < 1) {
            Toast.makeText(getContext(), "Quantity is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            TextView budget = binding.textBudget;
            double toyPrice;
            try {
                toyPrice = Double.parseDouble(toy.getPrice());
                budgetDouble += toyPrice * quantity;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Price format is incorrect", Toast.LENGTH_SHORT).show();
                return;
            }

            ConSQL connectionClass = new ConSQL();
            Connection conn = connectionClass.conclass();
            LocalDate today = LocalDate.now();
            Date sqlDate = Date.valueOf(String.valueOf(today));

            if (conn != null) {
                try {
                    String query = "INSERT INTO PurchasedToys (toy_id, purchase_date, quantity) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(toy.getToy_id()));
                    stmt.setDate(2, sqlDate);
                    stmt.setInt(3, quantity);

                    stmt.executeUpdate();

                    String budgetString = String.format("Cost: %.2f", budgetDouble);
                    budget.setText(budgetString);

                    toyListBuy.add(toy);
                    stmt.close();
                    conn.close();

                    Toast.makeText(getContext(), "Toy purchased successfully!", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Purchase failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setBudgetTextView(TextView budget) {
    }
}

