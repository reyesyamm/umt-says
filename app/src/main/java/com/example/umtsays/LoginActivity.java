package com.example.umtsays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.umtsays.modelos.Usuario;
import com.example.umtsays.utilidades.SesionUsuario;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    EditText txtCorreo;
    EditText txtContrasena;
    MaterialButton btnIniciarSesion;
    MaterialButton btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = txtCorreo.getText().toString();
                String contrasena = txtContrasena.getText().toString();

                if(esCorreoValido(correo)){

                    if(esContrasenaValida(contrasena)){

                        Usuario usuarioTemporal = new Usuario(1, "Juan", "Perez", correo, contrasena);

                        new SesionUsuario().guardarUsuarioSesion(getApplicationContext(), usuarioTemporal);

                        Intent intentListado = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentListado);

                    }else{
                        Toast.makeText(LoginActivity.this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intentRegistro);

            }
        });

        Usuario usuarioLogueado = new SesionUsuario().obtenerUsuarioSesion(getApplicationContext());
        if(usuarioLogueado != null){
            Intent intentListado = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentListado);
        }

    }

    boolean esCorreoValido(String correo){
        return !correo.trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    boolean esContrasenaValida(String contrasena){
        return !contrasena.trim().isEmpty();
    }

}