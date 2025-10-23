package org.examen;

import org.examen.*;

import org.examen.domainLayer.*;
import org.examen.presentationLayer.controllers.*;
import org.examen.presentationLayer.models.*;
import org.examen.presentationLayer.views.*;
import org.examen.utilities.FileManagement;
import org.examen.serviceLayer.*;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        var fileStore = FileManagement.getUsuarioFileStore("usuarios.xml");
//        var elements = fileStore.readAll();
//        for(Usuario element : elements){
//            System.out.println(element.toString());
//        }


         IService<Usuario> usuarioService = new UsuarioService(FileManagement.getUsuarioFileStore("usuarios.xml"));
        IService<Proyecto> proyectoService = new ProyectoService(FileManagement.getProyectoFileStore("proyectos.xml"));
        IService<Tarea> tareaService = new TareaService(FileManagement.getTareaFileStore("tareas.xml"));
        ProyectoController proyectoController = new ProyectoController(proyectoService);
        TareaController tareaController =  new TareaController(tareaService);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        ProyectoTableModel proyectoTableModel = new ProyectoTableModel();
        TareaTableModel tareaTableModel = new TareaTableModel();
        proyectoService.addObserver(proyectoTableModel);
        tareaService.addObserver(tareaTableModel);
        List<Proyecto> proyectos = proyectoService.leerTodos();
        List<Tarea> tareas = tareaService.leerTodos();
        MainView ventana = new MainView(proyectoTableModel,proyectoController,proyectos,tareaController);

        ventana.getContentPanel();
        ventana.setVisible(true);

    }
}