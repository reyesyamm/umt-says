package com.example.umtsays.interfaces;

import com.example.umtsays.modelos.Mensaje;

public interface AdaptadorMensajeListener {

    void onClickMensaje(Mensaje mensaje, int position);
    void onClickEliminarMensaje(Mensaje mensaje, int position);

}
