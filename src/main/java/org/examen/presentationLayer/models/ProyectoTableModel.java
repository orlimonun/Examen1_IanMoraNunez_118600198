package org.examen.presentationLayer.models;

import org.examen.domainLayer.Proyecto;
import org.examen.serviceLayer.IServiceObserver;
import org.examen.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ProyectoTableModel extends AbstractTableModel implements IServiceObserver<Proyecto>{

    private final String[] cols = {"NumeroProyecto","Descripcion","Encargado"};
    private final Class<?>[] types = {Integer.class, String.class, String.class};
    private final List<Proyecto> rows = new ArrayList<>();

    public void setRows(List<Proyecto> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public Proyecto getAt(int row) {return (row >= 0 && row < rows.size()) ? rows.get(row) : null;}

    @Override public int getRowCount() {return rows.size();}
    @Override public int getColumnCount() {return cols.length;}
    @Override public String getColumnName(int c) {return cols[c];}
    @Override public Class<?> getColumnClass(int c) {return types[c];}
    @Override public boolean isCellEditable(int r, int c) {return false;}

    @Override
    public Object getValueAt(int r, int c) {
        //numeroPrescripcion, paciente, estado, fechaDeConfeccion, fechaDeRetiro, listaRecetas
        Proyecto x = rows.get(r);
        switch (c) {
            case 0: return x.getNumProyecto();
            case 1: return x.getDescripcion();
            case 2: return x.getEncargadoGeneral();
            default: return null;
        }
    }

    @Override
    public void onDataChanged(ChangeType type, Proyecto entity) {
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

    private int indexOf(Proyecto entity) {
        int i=-1;
        for (int j = 0; j < rows.size(); j++) {
            if (rows.get(j).getNumProyecto() == entity.getNumProyecto()){
                i=j;
                break;
            }
        }
        return i;
    }
    public List<Proyecto> getRows() { return new ArrayList<>(rows); }
}

