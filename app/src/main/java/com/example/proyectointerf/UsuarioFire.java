package com.example.proyectointerf;

public class UsuarioFire {
    String nombre,correo,calle,colonia,foto,codigopostal;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public UsuarioFire(String nombre, String correo, String calle, String colonia, String foto, String codigopostal) {
        this.nombre = nombre;
        this.correo = correo;
        this.calle = calle;
        this.colonia = colonia;
        this.foto = foto;
        this.codigopostal = codigopostal;
    }
}
