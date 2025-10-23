package org.examen.serviceLayer;

import org.examen.dataLayer.IFileStore;
import org.examen.domainLayer.Proyecto;
import org.examen.domainLayer.Tarea;
import org.examen.utilities.ChangeType;

import java.util.ArrayList;
import java.util.List;

public class TareaService implements IService<Tarea>{

    private final IFileStore<Tarea> fileStore;
    private final List<IServiceObserver<Tarea>> listeners = new ArrayList<>();

    public TareaService(IFileStore<Tarea> fileStore) {this.fileStore = fileStore;}

    public void agregar(Tarea prescripcion) {
        List<Tarea> prescripcions= fileStore.readAll();

        //El siquiente segmento de codigo asigna un numero de prescripcion unico e incremental a cada prescripcion
        int nuevoNumero = prescripcions.stream()
                .mapToInt(Tarea::getNumero)
                .max()
                .orElse(0) + 1;
        prescripcion.setNumero(nuevoNumero);

        prescripcions.add(prescripcion);
        fileStore.writeAll(prescripcions);
        notifyObservers(ChangeType.CREATED, prescripcion);

    }
    private void notifyObservers(ChangeType type, Tarea entity) {
        for (IServiceObserver<Tarea> l: listeners) l.onDataChanged(type, entity);
    }
    @Override
    public void borrar(String codigo) {
        //
    }

    @Override
    public void actualizar(Tarea receta) {
        List<Tarea> recetas= fileStore.readAll();
        for (int i=0; i<recetas.size();i++){
            if (recetas.get(i).getNumero() == receta.getNumero()){
                recetas.set(i, receta);
            }
        }
        fileStore.writeAll(recetas);
        notifyObservers(ChangeType.UPDATED, receta);
    }

    @Override public List<Tarea> leerTodos() {
        return fileStore.readAll();
    }

    @Override
    public Tarea leerPorCodigo(String codigo) {
        return  fileStore.readAll().stream().filter(a -> a.getDescripcion().equals(codigo)).findFirst().orElse(null);
    }

    @Override
    public void addObserver(IServiceObserver<Tarea> l) {if (l!=null) listeners.add(l);}

    @Override public Tarea leerPorId(int id) {return null;}
    @Override public void borrar(int id) {}


}

