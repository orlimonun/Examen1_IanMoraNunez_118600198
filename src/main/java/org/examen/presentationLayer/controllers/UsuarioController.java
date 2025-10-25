package org.examen.presentationLayer.controllers;

import org.examen.domainLayer.Usuario;
import org.examen.serviceLayer.IService;

import java.util.List;

public class UsuarioController {

    private final IService<Usuario> service;

    public UsuarioController(IService<Usuario> service) {
        this.service = service;
    }

    private void validarId(int id){if(id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0");}

    private void validarNombre(String nombre){
        if(nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio. ");
    }

    public void agregar(int id, String nombreCompleto, String email){
        validarId(id);
        validarNombre(nombreCompleto);
        Usuario a = new Usuario(Integer.toString(id), nombreCompleto, email);
        service.agregar(a);
    }

    public void actualizar(int id, String nombreCompleto, String email){
        validarId(id);
        validarNombre(nombreCompleto);
        Usuario a = new Usuario(Integer.toString(id),nombreCompleto , email);
        service.actualizar(a);
    }
    public void borrar(int id){
        validarId(id);
        service.borrar(id);
    }
    public List<Usuario> leerTodos(){
        return service.leerTodos();
    }

    public Usuario leerPorId(int id){
        validarId(id);
        return service.leerPorId(id);
    }


}