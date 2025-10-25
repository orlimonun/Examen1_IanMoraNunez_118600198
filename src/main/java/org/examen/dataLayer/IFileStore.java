package org.examen.dataLayer;

import java.util.List;
public interface IFileStore<T> {

    List<T> readAll();
    void writeAll(List<T> data);
}

