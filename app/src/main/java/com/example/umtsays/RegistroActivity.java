package com.example.umtsays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.umtsays.modelos.Usuario;
import com.example.umtsays.utilidades.SesionUsuario;
import com.google.android.material.button.MaterialButton;

public class RegistroActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtApellidos;
    EditText txtCorreo;
    EditText txtContrasena;
    ProgressBar progressBar;
    MaterialButton btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);
        progressBar = findViewById(R.id.progressBar);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarUsuario();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void registrarUsuario(){

        String nombre = txtNombre.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String correo = txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();

        if(nombre.trim().isEmpty()){
            txtNombre.setError("Capture un nombre");
            return;
        }else{
            txtNombre.setError(null);
        }

        if(apellidos.trim().isEmpty()){
            txtApellidos.setError("Capture sus apellidos");
            return;
        }else{
            txtApellidos.setError(null);
        }

        if(correo.trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            txtCorreo.setError("Capture un correo válido");
            return;
        }else{
            txtCorreo.setError(null);
        }

        if(contrasena.trim().isEmpty()){
            txtContrasena.setError("Capture su contraseña");
            return;
        }else{
            txtContrasena.setError(null);
        }

        Usuario usuarioNuevo = new Usuario(1, nombre, apellidos, correo, contrasena);

        new SesionUsuario().guardarUsuarioSesion(getApplicationContext(), usuarioNuevo);

        progressBar.setVisibility(View.VISIBLE);
        btnRegistro.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);
                btnRegistro.setEnabled(true);

                Toast.makeText(RegistroActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                Intent intentListado = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intentListado);


            }
        }, 3000);



    }

}