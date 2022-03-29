package com.example.umtsays.modelos;

import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {

    public enum TipoMensaje{
        NOTICIA, AVISO, EVENTO
    }

    // atributos
    int id;
    String titulo;
    String contenido;
    TipoMensaje tipo;
    String autor;
    int usuario_id;
    Date fecha;

    public Mensaje(int id, String titulo, String contenido, TipoMensaje tipo, String autor, int usuario_id, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
        this.autor = autor;
        this.usuario_id = usuario_id;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
