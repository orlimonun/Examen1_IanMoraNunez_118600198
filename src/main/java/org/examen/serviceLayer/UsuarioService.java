package org.examen.serviceLayer;

import org.examen.dataLayer.IFileStore;
import org.examen.domainLayer.Usuario;
import org.examen.utilities.ChangeType;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService implements IService<Usuario>{

    private final IFileStore<Usuario> fileStore;
    private final List<IServiceObserver<Usuario>> listeners = new ArrayList<>();

    public UsuarioService(IFileStore<Usuario> fileStore) {
        this.fileStore = fileStore;
    }

    public void agregar(Usuario administrador){
        List<Usuario> administradores = fileStore.readAll();
        administradores.add(administrador);
        fileStore.writeAll(administradores);
        notifyObservers(ChangeType.CREATED, administrador);
    }
    @Override
    public void borrar(int id){
        List<Usuario> administradores = fileStore.readAll();
        Usuario removed = null;
        for(int i = 0; i < administradores.size(); i++){
            if(Integer.parseInt(administradores.get(i).getId()) == id){
                removed = administradores.remove(i); break;
            }
        }
        fileStore.writeAll(administradores);
        if(removed != null) notifyObservers(ChangeType.DELETED, removed);
    }

    @Override
    public List<Usuario> leerTodos(){
        return fileStore.readAll();
    }

    @Override
    public Usuario leerPorId(int id){
       String ident = Integer.toString(id);
        return fileStore.readAll().stream().filter(a ->a.getId().equals(ident) ).findFirst().orElse(null);
    }

    @Override
    public void addObserver(IServiceObserver<Usuario> l){if(l != null) listeners.add(l);}
    private void notifyObservers(ChangeType type, Usuario entity){
        for(IServiceObserver<Usuario> l : listeners) l.onDataChanged(type,entity);
    }

    @Override
    public void actualizar(Usuario administrador){
        List<Usuario> administradores = fileStore.readAll();
        for(int i = 0; i < administradores.size(); i++){
            if(administradores.get(i).getId().equals(administrador.getId())){
                administradores.set(i,administrador);
            }
        }
        fileStore.writeAll(administradores);
        notifyObservers(ChangeType.UPDATED, administrador);
    }

    @Override public Usuario leerPorCodigo(String codigo) {return null;}
    @Override public void borrar(String codigo) {}



}
