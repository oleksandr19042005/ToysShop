package com.example.toysshop.ui.exit;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.toysshop.MainActivity;
import com.example.toysshop.R;
import com.example.toysshop.databinding.FragmentExitBinding;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;

public class ExitFragment extends Fragment {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private String userType;
    private FragmentExitBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ExitViewModel exitViewModel = new ViewModelProvider(this).get(ExitViewModel.class);
        binding = FragmentExitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        drawer = getActivity().findViewById(R.id.drawer_layout);
        navigationView = getActivity().findViewById(R.id.nav_view);


        Button loginButtonAdmin = root.findViewById(R.id.btnLoginAdmin);
        loginButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.inputName.getText().toString().trim();
                String password = binding.inputPassword.getText().toString().trim();
                authenticateUser(username, password);
            }
        });

        Button loginButtonUser = root.findViewById(R.id.btnLoginUser);
        loginButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = "user";
                setupNavigationMenu();
            }
        });

        return root;
    }

    private void authenticateUser(String name, String password) {
        String correctName = "admin";
        String correctPassword = "admin";

        if (name.equals(correctName) && password.equals(correctPassword)) {
            userType = "admin";
            setupNavigationMenu();
        } else {
            binding.msgErrorAutetification.setText("No such admin found");
        }
    }

    public void setupNavigationMenu() {
        Menu menu = navigationView.getMenu();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

        if ("admin".equals(userType)) {
            navController.navigate(R.id.nav_add_toy);
            menu.findItem(R.id.nav_add_toy).setVisible(true);
            menu.findItem(R.id.nav_print_toy).setVisible(true);
            menu.findItem(R.id.nav_buy_toy).setVisible(false);
            menu.findItem(R.id.nav_support).setVisible(false);
            menu.findItem(R.id.nav_exit).setVisible(true);
        } else if ("user".equals(userType)) {
            navController.navigate(R.id.nav_buy_toy);
            menu.findItem(R.id.nav_support).setVisible(true);
            menu.findItem(R.id.nav_buy_toy).setVisible(true);
            menu.findItem(R.id.nav_add_toy).setVisible(false);
            menu.findItem(R.id.nav_print_toy).setVisible(false);
            menu.findItem(R.id.nav_exit).setVisible(true);
        }
    }

}
