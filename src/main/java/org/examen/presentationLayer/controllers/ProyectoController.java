package org.examen.presentationLayer.controllers;

import org.examen.domainLayer.Proyecto;
import org.examen.domainLayer.Tarea;
import org.examen.serviceLayer.IService;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class ProyectoController {

    private final IService<Proyecto> service;

    public ProyectoController(IService<Proyecto> service) {this.service = service;}

    public void agregar(String descripcion, String encargadoGeneral,int numProyecto, List<Tarea> listaRecetas){
        // if (validar(estado, fechaDeConfeccion, fechaDeRetiro, numeroPrescripcion, pacienteID)){
        Proyecto prescripcion = new Proyecto(descripcion, encargadoGeneral, numProyecto);
        prescripcion.setListaTareas(listaRecetas); // ver nota abajo sobre setListaRecetas
        service.agregar(prescripcion);
        //}
    }
    public void actualizar(String descripcion, String encargadoGeneral,int numProyecto){
        if (validar(descripcion, encargadoGeneral,numProyecto)){

            Proyecto prescripcion = new Proyecto( descripcion, encargadoGeneral, numProyecto);
            service.actualizar(prescripcion);
        }
    }

    public void borrar(int numeroPrescripcion){ // no usar las prescripciones una ves creadas no se pueden borrar
        if(numeroPrescripcion != 0) {
            service.borrar(numeroPrescripcion);
        }

    }

    public List<Proyecto> leerTodos(){return service.leerTodos();}

    public Proyecto leerPorNumero(int numeroPrescripcion){
        return service.leerPorId(numeroPrescripcion);
    }

    public List<Proyecto> getProyectoByPacienteId(int pacienteID){
        List<Proyecto> prescripcionesPaciente = new ArrayList<>();
        List<Proyecto> todasLasPrescripciones = service.leerTodos();
        for (Proyecto pres : todasLasPrescripciones){
            if (pres.getNumProyecto() == pacienteID){
                prescripcionesPaciente.add(pres);
            }
        }
        return prescripcionesPaciente;
    }

    //Validaciones
    private boolean validar(String descripcion, String encargadoGeneral,int numProyecto){
        boolean esValido = true;
        if (estaVacio(descripcion) ){
            //ninguno de los campos String pueden ser nulos o vacios
            esValido = false;
        } else if (numProyecto==0) { //numeroPrescripcion no pueden ser 0
            esValido = false;
        } else if (estaVacio(encargadoGeneral)) { //las fechas no pueden ser nulas y la fecha de retiro no puede ser anterior a la de confeccion
            esValido = false;
        }
        return esValido;
    }

    private boolean estaVacio(String s){
        return s == null || s.isEmpty();
    }

}
