package com.example.login;

public class ListItem {
    private String id;
    private String title;
    private String descripcion;


    public ListItem(String title, String id, String descripcion) {
        this.id = id;
        this.title = title;
        this.descripcion = descripcion;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}