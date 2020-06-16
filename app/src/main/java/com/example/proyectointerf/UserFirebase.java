package com.example.proyectointerf;

public class UserFirebase {
    String id_local,id_fire,nombre,correo,calle,colonia,foto,codigopost;

    public UserFirebase() {
    }

    public String getId_local() {
        return id_local;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    public String getId_fire() {
        return id_fire;
    }

    public void setId_fire(String id_fire) {
        this.id_fire = id_fire;
    }

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

    public String getCodigopost() {
        return codigopost;
    }

    public void setCodigopost(String codigopost) {
        this.codigopost = codigopost;
    }
}
