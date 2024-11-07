package com.ulpsoft.inmobiliaria_final;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ulpsoft.inmobiliaria_final.databinding.ActivityMenuBinding;
import com.ulpsoft.inmobiliaria_final.model.Usuario;

public class Menu extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private MenuViewModel menuViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_inmueble, R.id.nav_perfil, R.id.nav_inquilino, R.id.nav_contrato)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.cargarUsuario(this); // Carga el usuario

        menuViewModel.getUsuario().observe(this, usuario -> {
            cargarEncabezado(usuario);
        });


        // Procesar el intent para extraer el Deep Link
        menuViewModel.processDeepLink(getIntent());
    }

    private void cargarEncabezado(Usuario usuario) {
        View headerView = binding.navView.getHeaderView(0);

        TextView tvNombreCompleto = headerView.findViewById(R.id.tvNombreCompleto);
        TextView tvEmail = headerView.findViewById(R.id.textView); // Cambiado a textView
        ImageView ivAvatar = headerView.findViewById(R.id.ivAvatar);

        tvNombreCompleto.setText(usuario.getNombreCompleto());
        tvEmail.setText(usuario.getEmail());
        Glide.with(this)
                .load(usuario.getAvatar())
                .into(ivAvatar);
    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
