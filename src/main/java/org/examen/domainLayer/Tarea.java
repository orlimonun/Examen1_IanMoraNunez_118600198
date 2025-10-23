package org.examen.domainLayer;

import jakarta.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name = "tarea")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Proyecto.class})
public class Tarea {

    @XmlElement(name ="numero")
    private int numero;
    @XmlElement(name ="descripcion")
    private String descripcion;
    @XmlElement(name ="fechaFinalizacion")
    private Date fechaFinalizacion;
    @XmlElement(name ="prioridad")
    private String prioridad;
    @XmlElement(name ="estado")
    private String estado ;
    @XmlElement(name ="encargado")
    private String encargado;

    public Tarea(int numero, String descripcion, Date fechaFinalizacion, String prioridad, String estado, String encargado) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.encargado = encargado;
    }

    public Tarea() {
    }

    public int getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
}
