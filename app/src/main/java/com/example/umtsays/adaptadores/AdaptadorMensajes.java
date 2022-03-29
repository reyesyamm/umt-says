package com.example.umtsays.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umtsays.R;
import com.example.umtsays.interfaces.AdaptadorMensajeListener;
import com.example.umtsays.modelos.Mensaje;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdaptadorMensajes extends RecyclerView.Adapter<AdaptadorMensajes.MensajeViewHolder> {

    List<Mensaje> listado;
    AdaptadorMensajeListener listener;

    public AdaptadorMensajes(List<Mensaje> listado, AdaptadorMensajeListener listener) {
        this.listado = listado;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_item_view, parent, false);

        return new MensajeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        holder.configurar( listado.get(position) );
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    class MensajeViewHolder extends RecyclerView.ViewHolder{

        ImageView imgMensaje;
        TextView tvTitulo;
        TextView tvMensaje;
        TextView tvFecha;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMensaje = itemView.findViewById(R.id.imgMensaje);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            tvFecha = itemView.findViewById(R.id.tvFecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Mensaje mensaje = listado.get( getAdapterPosition() );

                    listener.onClickMensaje( mensaje, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Mensaje mensaje = listado.get( getAdapterPosition() );


                    listener.onClickEliminarMensaje( mensaje, getAdapterPosition());

                    return true;
                }
            });

        }

        void configurar(Mensaje mensaje){

            tvTitulo.setText(mensaje.getTitulo());
            tvMensaje.setText(mensaje.getContenido());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = df.format(mensaje.getFecha());
            tvFecha.setText(fechaFormateada);

            switch (mensaje.getTipo()){
                case AVISO:
                    imgMensaje.setImageResource(R.drawable.aviso);
                    break;
                case EVENTO:
                    imgMensaje.setImageResource(R.drawable.evento);
                    break;
                case NOTICIA:
                    imgMensaje.setImageResource(R.drawable.noticia);
                    break;
            }

        }

    }

}
