package org.examen.utilities;

import org.examen.dataLayer.*;
import org.examen.domainLayer.*;

import java.io.File;

public class FileManagement {

    static File baseDir = new File(System.getProperty("user.dir"));

    public static IFileStore<Proyecto> getProyectoFileStore(String fileName){
        File proyectosXml = new File(baseDir,fileName);
        return new ProyectoFileStore(proyectosXml);
    }
    public static IFileStore<Tarea> getTareaFileStore(String fileName){
        File proyectosXml = new File(baseDir,fileName);
        return new TareaFileStore(proyectosXml);
    }
    public static IFileStore<Usuario> getUsuarioFileStore(String fileName){
        File proyectosXml = new File(baseDir,fileName);
        return new UsuarioFileStore(proyectosXml);
    }

}
