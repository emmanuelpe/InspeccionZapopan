package com.perspective.inszap;

public class Articulo {

    private int articulo;
    private String descripcion;
    private String tipo;

    public Articulo(){
        this.tipo="";
        this.descripcion="";
    }

    public int getArticulo() {
        return articulo;
    }

    public void setArticulo(int articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




    @Override
    public String toString() {
        return "Articulo: "+this.articulo+"\nDescripcion: "+this.descripcion+"\nTipo: "+this.tipo;
    }

}

