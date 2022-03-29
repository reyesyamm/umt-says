package com.example.umtsays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.umtsays.modelos.Mensaje;
import com.example.umtsays.modelos.Usuario;
import com.example.umtsays.utilidades.SesionUsuario;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class FormularioMensajeActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtContenido;
    RadioButton rbTipoNoticia;
    RadioButton rbTipoAviso;
    RadioButton rbTipoEvento;
    MaterialButton btnGuardar;

    Mensaje mensajeEditar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_mensaje);

        if(getSupportActionBar() != null){

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtTitulo = findViewById(R.id.txtTitulo);
        txtContenido = findViewById(R.id.txtContenido);
        rbTipoNoticia = findViewById(R.id.rbTipoNoticia);
        rbTipoAviso = findViewById(R.id.rbTipoAviso);
        rbTipoEvento = findViewById(R.id.rbTipoEvento);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardarMensaje();

            }
        });

        mensajeEditar = (Mensaje) getIntent().getSerializableExtra("mensaje");

        if(mensajeEditar != null){

            getSupportActionBar().setTitle("Editar Mensaje");

            txtTitulo.setText(mensajeEditar.getTitulo());
            txtContenido.setText(mensajeEditar.getContenido());

            rbTipoNoticia.setChecked( mensajeEditar.getTipo() == Mensaje.TipoMensaje.NOTICIA );
            rbTipoAviso.setChecked( mensajeEditar.getTipo() == Mensaje.TipoMensaje.AVISO );
            rbTipoEvento.setChecked( mensajeEditar.getTipo() == Mensaje.TipoMensaje.EVENTO );

        }else{
            getSupportActionBar().setTitle("Nuevo Mensaje");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    void guardarMensaje(){

        String titulo = txtTitulo.getText().toString();
        String contenido = txtContenido.getText().toString();

        Mensaje.TipoMensaje tipo = null;
        if(rbTipoNoticia.isChecked()){
            tipo = Mensaje.TipoMensaje.NOTICIA;
        }else if(rbTipoAviso.isChecked()){
            tipo = Mensaje.TipoMensaje.AVISO;
        }else if(rbTipoEvento.isChecked()){
            tipo = Mensaje.TipoMensaje.EVENTO;
        }

        if(titulo.trim().isEmpty()){
            txtTitulo.setError("Ingrese un título");
            return;
        }else{
            txtTitulo.setError(null);
        }

        if(contenido.trim().isEmpty()){
            txtContenido.setError("Ingrese el contenido del mensaje");
            return;
        }else{
            txtContenido.setError(null);
        }

        if(tipo == null){
            Toast.makeText(this, "Seleccione el tipo de mensaje", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new SesionUsuario().obtenerUsuarioSesion(getApplicationContext());

        String nombreAutor = usuario.getNombre()+" "+usuario.getApellidos();

        int id = mensajeEditar != null ? mensajeEditar.getId() : 0;

        Mensaje mensaje = new Mensaje(id, titulo, contenido, tipo, nombreAutor, usuario.getId(), new Date());

        Intent intent = getIntent();
        intent.putExtra("mensaje", mensaje);

        setResult(RESULT_OK, intent);

        if(mensajeEditar != null){
            Toast.makeText(this, "Mensaje actualizado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Mensaje creado", Toast.LENGTH_SHORT).show();
        }

        finish();

    }
}