package org.examen.presentationLayer.models;

import org.examen.domainLayer.Tarea;
import org.examen.serviceLayer.IServiceObserver;
import org.examen.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TareaTableModel extends AbstractTableModel implements IServiceObserver<Tarea>{

    private final String[] cols = {"Numero","Descripcion","Vence","prioridad","Estado","Asignado a"};
    private final Class<?>[] types = {Integer.class, String.class, Date.class, String.class, String.class, String.class};
    private final List<Tarea> rows = new ArrayList<>();

    public void setRows(List<Tarea> data) {
        this.rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }
    public List<Tarea> getRows() { return new ArrayList<>(rows); }

    public Tarea getAt(int row) {return (row >= 0 && row < rows.size()) ? rows.get(row) : null;}

    @Override public int getRowCount() {return rows.size();}
    @Override public int getColumnCount() {return cols.length;}
    @Override public String getColumnName(int c) {return cols[c];}
    @Override public Class<?> getColumnClass(int c) {return types[c];}
    @Override public boolean isCellEditable(int r, int c) {return false;}

    @Override
    public Object getValueAt(int r, int c) {
        //codigo, nombre, presentacion,  cantidad,  duracion, indicaciones, numeroPrescripcion
        Tarea x = rows.get(r);
        switch (c) {
            case 0: return x.getNumero();
            case 1: return x.getDescripcion();
            case 2: return x.getFechaFinalizacion();
            case 3: return x.getPrioridad();
            case 4: return x.getEstado();
            case 5: return x.getEncargado();
            default: return null;
        }
    }

    @Override
    public void onDataChanged(ChangeType type, Tarea entity) {
        switch (type) {
            case CREATED: {
                rows.add(entity);
                int i = rows.size() - 1;
                fireTableRowsInserted(i, i);
                break;
            }
            case UPDATED: {
                int i = indexOf(entity);
                if (i >= 0) {
                    rows.set(i, entity);
                    fireTableRowsUpdated(i, i);
                }
                break;
            }
            case DELETED: {
                int i = indexOf(entity);
                if (i >= 0) {
                    rows.remove(i);
                    fireTableRowsDeleted(i, i);
                }
                break;
            }
        }



    }
    private int indexOf(Tarea rec) {
        int recetaACambiar = rec.getNumero();
        int i = -1; //se inicia en 0 por si el primer elemento no se encuentra
        for (int j = 0; j < rows.size(); j++) {
            //Iterar sobre la lista rows para encontrar al objeto con el mismo codigo
            if (rows.get(j).getNumero() == recetaACambiar) {
                i = j;//si coincide el id, se guarda la posicion
                break;
            }
        }
        return i;
    }


}