package org.examen.serviceLayer;

import org.examen.utilities.ChangeType;

public interface IServiceObserver<T> {

    void onDataChanged(ChangeType type, T entity);

}
