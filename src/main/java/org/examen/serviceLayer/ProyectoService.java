package org.examen.serviceLayer;

import org.examen.domainLayer.Proyecto;
import org.examen.dataLayer.IFileStore;
import org.examen.utilities.ChangeType;

import java.util.ArrayList;
import java.util.List;

public class ProyectoService implements IService<Proyecto>{
    private final IFileStore<Proyecto> fileStore;
    private final List<IServiceObserver<Proyecto>> listeners = new ArrayList<>();

    public ProyectoService(IFileStore<Proyecto> fileStore) {this.fileStore = fileStore;}

    private void notifyObservers(ChangeType type, Proyecto entity) {
        for (IServiceObserver<Proyecto> l: listeners) l.onDataChanged(type, entity);
    }

    @Override
    public void agregar(Proyecto prescripcion) {
        List<Proyecto> prescripcions= fileStore.readAll();

        //El siquiente segmento de codigo asigna un numero de prescripcion unico e incremental a cada prescripcion
        int nuevoNumero = prescripcions.stream()
                .mapToInt(Proyecto::getNumProyecto)
                .max()
                .orElse(0) + 1;
        prescripcion.setNumProyecto(nuevoNumero);

        prescripcions.add(prescripcion);
        fileStore.writeAll(prescripcions);
        notifyObservers(ChangeType.CREATED, prescripcion);

    }

    @Override
    public void actualizar(Proyecto entity) {
        List<Proyecto> prescripcions= fileStore.readAll();
        for (int i=0; i<prescripcions.size();i++){
            Proyecto pre = prescripcions.get(i);
            if (pre.getNumProyecto() == entity.getNumProyecto()){
                entity.setListaTareas(pre.getListaTareas());
                // Mantener la lista de recetas existente ya todos los demas atributos se mantuvieron, excepto por el estado
                prescripcions.set(i, entity);
            }
        }
        fileStore.writeAll(prescripcions);
        notifyObservers(ChangeType.UPDATED, entity);
    }

    @Override public List<Proyecto> leerTodos() {return fileStore.readAll();}

    @Override
    public Proyecto leerPorId(int numeroProyecto) {
        return fileStore.readAll().stream().filter(a -> a.getNumProyecto() == numeroProyecto).findFirst().orElse(null) ;
    }

    @Override
    public void addObserver(IServiceObserver<Proyecto> l) {
        if(l != null) listeners.add(l);
    }

    @Override public Proyecto leerPorCodigo(String codigo) {
        return null;
    }
    @Override public void borrar(int id) {} //Las prescripciones no se pueden borrar una ves creadas y ingresadas en el sistema
    @Override public void borrar(String codigo) {}
}
