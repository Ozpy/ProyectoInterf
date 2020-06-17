package com.example.proyectointerf;

public class ContactoVo {
    private String nombre;
    private String email;
    private int foto;
    private String estado;

    public ContactoVo(String nombre, String email, int foto, String estado) {
        this.nombre = nombre;
        this.email = email;
        this.foto = foto;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}
}
