package org.examen.presentationLayer.controllers;

import org.examen.domainLayer.Tarea;
import org.examen.serviceLayer.IService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TareaController {

    private final IService<Tarea> service;

    public TareaController(IService<Tarea> service) {this.service = service;}

    public void agregar(int numero, String descripcion, Date fechaFinalizacion, String prioridad, String estado, String encargado){
        // if (validar(numeroPrescripcion, codigo, nombre, presentacion, cantidad, duracion, indicaciones)){
        int proyecto = numeroTarea(numero)+1;
        Tarea receta = new Tarea(numero, proyecto, descripcion, fechaFinalizacion, prioridad, estado, encargado);
        service.agregar(receta);
        // }
    }

    public int numeroTarea(int numero){
        List<Tarea> tareas= service.leerTodos();
        int mayor=0;
        for (Tarea rec : tareas){
            if (rec.getProyecto()== numero){
                mayor++;
            }
        }
        return mayor;
    }

    public void actualizar(int numero, int proyecto, String descripcion, Date fechaFinalizacion, String prioridad, String estado, String encargado){
        if (validar(numero, descripcion, fechaFinalizacion, prioridad, estado, encargado)){
            Tarea receta = new Tarea(numero, proyecto, descripcion, fechaFinalizacion, prioridad, estado, encargado);
            service.actualizar(receta);
        }
    }

    public void borrar(String codigo){
        if(!estaVacio(codigo)) {
            service.borrar(codigo);
        }
    }

    public List<Tarea> leerTodos(){return service.leerTodos();}

    public List<Tarea> leePorNumero(int proyecto){
        List<Tarea> tareas= service.leerTodos();
        List<Tarea> tareasFiltradas= new ArrayList<>();
        for (Tarea rec : tareas){
            if (rec.getNumero() == proyecto){
                tareasFiltradas.add(rec);
            }
        }
        return tareasFiltradas;
    }

    public Tarea leerPorCodigo(String codigo){
        if(!estaVacio(codigo)) {
            return service.leerPorCodigo(codigo);
        }
        return null;
    }

    //Validaciones

    private boolean validar(int numero, String descripcion, Date fechaFinalizacion, String prioridad, String estado, String encargado) {
        boolean esValido = true;
        if (estaVacio(descripcion) || estaVacio(prioridad) || estaVacio(estado) || estaVacio(encargado)) {//son vacios o nulos
            esValido = false;
        } else if (numero==0) { //numeroPrescripcion, cantidad y duracion no pueden ser 0
            esValido = false;
        }
        else if (fechaFinalizacion== null) { //numeroPrescripcion, cantidad y duracion no pueden ser 0
            esValido = false;
        }
        return esValido;
    }

    private boolean estaVacio(String s){
        return s == null || s.isEmpty();
    }
}



