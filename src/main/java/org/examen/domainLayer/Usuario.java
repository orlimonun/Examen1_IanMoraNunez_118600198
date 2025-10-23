package org.examen.domainLayer;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "name")
    private String nombre;
    @XmlElement(name = "email")
    private String email;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

