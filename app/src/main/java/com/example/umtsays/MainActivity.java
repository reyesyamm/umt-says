package com.example.umtsays;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.umtsays.adaptadores.AdaptadorMensajes;
import com.example.umtsays.interfaces.AdaptadorMensajeListener;
import com.example.umtsays.modelos.Mensaje;
import com.example.umtsays.utilidades.SesionUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    FloatingActionButton btnNuevoMensaje;

    AdaptadorMensajes adapter;
    LinearLayoutManager llm;
    List<Mensaje> listado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        btnNuevoMensaje = findViewById(R.id.btnNuevoMensaje);

        adapter = new AdaptadorMensajes(listado, new AdaptadorMensajeListener() {
            @Override
            public void onClickMensaje(Mensaje mensaje, int position) {

                Intent intentForm = new Intent(MainActivity.this, FormularioMensajeActivity.class);

                intentForm.putExtra("mensaje", mensaje);
                intentForm.putExtra("position", position);

                resultadoFormularioMensaje.launch(intentForm);

            }

            @Override
            public void onClickEliminarMensaje(Mensaje mensaje, int position) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Estás seguro de eliminar este mensaje?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                listado.remove(position);
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position, listado.size() - position);

                                Toast.makeText(MainActivity.this, "Mensaje eliminado", Toast.LENGTH_SHORT).show();

                                dialogInterface.dismiss();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();


            }
        });

        llm = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                obtenerDatos();

            }
        });

        btnNuevoMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentForm = new Intent(MainActivity.this, FormularioMensajeActivity.class);

                resultadoFormularioMensaje.launch(intentForm);

            }
        });

        obtenerDatos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_cerrar_sesion){

            new SesionUsuario().removerUsuarioSesion(getApplicationContext());

            Intent intentLogin = new Intent(this, LoginActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentLogin);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        moveTaskToBack(true);
    }

    void obtenerDatos(){

        swipeRefresh.setRefreshing(true);

        listado.clear();

        listado.add( new Mensaje(1, "Jornadas Próximas", "Las jornadas de aniversario estan próximas a iniciar", Mensaje.TipoMensaje.NOTICIA, "Juan Perez", 1, new Date()) );
        listado.add( new Mensaje(2,"Junta de alumnos", "El próximo 20 de febrero se tendrá una reunión para planificar las actividades de las jornadas", Mensaje.TipoMensaje.AVISO, "Juan Perez", 1, new Date()) );
        listado.add( new Mensaje(3,"Rally de Juegos Extremos", "Te invitamos a participar en el rally de juegos extremos, como parte de las jornadas de aniversario", Mensaje.TipoMensaje.EVENTO, "Juan Perez", 1, new Date()) );

        adapter.notifyDataSetChanged();

        swipeRefresh.setRefreshing(false);

    }

    ActivityResultLauncher<Intent> resultadoFormularioMensaje = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == RESULT_OK && result.getData() != null){

                        Intent data = result.getData();

                        int position = data.getIntExtra("position", -1);
                        Mensaje mensaje = (Mensaje) data.getSerializableExtra("mensaje");

                        if(position == -1){
                            listado.add(mensaje);
                            adapter.notifyItemInserted(listado.size() - 1);
                        }else{
                            listado.set(position, mensaje);
                            adapter.notifyItemChanged(position);
                        }

                    }

                }
            }
    );

}