package com.example.umtsays.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.umtsays.modelos.Usuario;

public class SesionUsuario {

    static final String KEY_DATOS_USUARIO = "datos_usuario";

    static final String KEY_ID = "usuario_id";
    static final String KEY_NOMBRE = "usuario_nombre";
    static final String KEY_APELLIDOS = "usuario_apellido";
    static final String KEY_CORREO = "usuario_correo";
    static final String KEY_CONTRASENA = "usuario_contrasena";

    public void guardarUsuarioSesion(Context context, Usuario usuario){

        SharedPreferences preferences = context.getSharedPreferences(KEY_DATOS_USUARIO, 0);
        SharedPreferences.Editor editorPreferences = preferences.edit();

        editorPreferences.putInt(KEY_ID, usuario.getId());
        editorPreferences.putString(KEY_NOMBRE, usuario.getNombre());
        editorPreferences.putString(KEY_APELLIDOS, usuario.getApellidos());
        editorPreferences.putString(KEY_CORREO, usuario.getCorreo());
        editorPreferences.putString(KEY_CONTRASENA, usuario.getContrasena());

        editorPreferences.apply();

    }

    @Nullable
    public Usuario obtenerUsuarioSesion(Context context){

        SharedPreferences preferences = context.getSharedPreferences(KEY_DATOS_USUARIO, 0);

        int id = preferences.getInt(KEY_ID, 0);
        String nombre = preferences.getString(KEY_NOMBRE, "");
        String apellidos = preferences.getString(KEY_APELLIDOS, "");
        String correo = preferences.getString(KEY_CORREO, "");
        String contrasena = preferences.getString(KEY_CONTRASENA, "");

        if(id == 0 || nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty()){

            return null;

        }else{

            return new Usuario(id, nombre, apellidos, correo, contrasena);

        }

    }

    public void removerUsuarioSesion(Context context){
        SharedPreferences preferences = context.getSharedPreferences(KEY_DATOS_USUARIO, 0);
        SharedPreferences.Editor editorPreferences = preferences.edit();
        editorPreferences.clear().apply();
    }

}
