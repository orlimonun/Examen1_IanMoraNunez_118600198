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
        Tarea receta = new Tarea(numero, descripcion, fechaFinalizacion, prioridad, estado, encargado);
        service.agregar(receta);
        // }
    }

    public void actualizar(int numero, String descripcion, Date fechaFinalizacion, String prioridad, String estado, String encargado){
        if (validar(numero, descripcion, fechaFinalizacion, prioridad, estado, encargado)){
            Tarea receta = new Tarea(numero, descripcion, fechaFinalizacion, prioridad, estado, encargado);
            service.actualizar(receta);
        }
    }

    public void borrar(String codigo){
        if(!estaVacio(codigo)) {
            service.borrar(codigo);
        }
    }

    public List<Tarea> leerTodos(){return service.leerTodos();}

    public List<Tarea> leePorNumero(int numeroPrescripcion){
        List<Tarea> recetaMedicas= service.leerTodos();
        List<Tarea> recetasFiltradas= new ArrayList<>();
        for (Tarea rec : recetaMedicas){
            if (rec.getNumero() == numeroPrescripcion){
                recetasFiltradas.add(rec);
            }
        }
        return recetasFiltradas;
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


