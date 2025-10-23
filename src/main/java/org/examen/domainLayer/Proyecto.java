package org.examen.domainLayer;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "proyecto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Proyecto {

    @XmlElement(name = "descripcion")
    private String descripcion;
    @XmlElement(name = "EncargadoGeneral")
    private String encargadoGeneral;
    @XmlTransient
    private List<Tarea> listaTareas = new ArrayList<>();
    @XmlElement(name = "numeroProyecto")
    private int numProyecto;
    public Proyecto() {
    }

    public Proyecto(String descripcion, String encargadoGeneral,int numProyecto) {
        this.descripcion = descripcion;
        this.encargadoGeneral = encargadoGeneral;
        this.numProyecto = numProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEncargadoGeneral() {
        return encargadoGeneral;
    }

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public int getNumProyecto() {
        return numProyecto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEncargadoGeneral(String encargadoGeneral) {
        this.encargadoGeneral = encargadoGeneral;

    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas= new ArrayList<>();
        for(Tarea t: listaTareas){
            if(t.getNumero() == this.getNumProyecto()){
                this.listaTareas.add(t);
            }
        }
    }

    public void setNumProyecto(int numProyecto) {
        this.numProyecto = numProyecto;
    }
}
