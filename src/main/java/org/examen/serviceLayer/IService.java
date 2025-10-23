package org.examen.serviceLayer;

import java.util.List;

public interface IService<T> {

    void agregar(T entity);
    void borrar(int id);
    void borrar(String codigo);//Codigo o nombre de un medico o persona
    void actualizar(T entity);
    List<T> leerTodos();
    T leerPorId(int id);
    T leerPorCodigo(String codigo);
    void addObserver(IServiceObserver<T> listener);

}
