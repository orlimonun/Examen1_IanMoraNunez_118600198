package org.examen.presentationLayer.views;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import org.examen.domainLayer.*;
import org.examen.dataLayer.*;
import org.examen.presentationLayer.controllers.*;
import org.examen.presentationLayer.models.*;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;


public class MainView extends JFrame {
    private JPanel MainPanel;
    private JPanel proyectosPanel;
    private JPanel TareasPanel;
    private JPanel formProyectoPanel;
    private JPanel TableProyectoPanel;
    private JTable tablaProyectos;
    private JPanel formTareaPanel;
    private JPanel TableTareaPanel;
    private JTable tablaTareas;
    private JPanel crearPanel;
    private JPanel descripcionPanel;
    private JPanel EncargadoPanel;
    private JButton crearProyecto;
    private JTextField descripcionTextField;
    private JComboBox encargadoComboBox;
    private JPanel crearTareaPanel;
    private JPanel descripcionTareaPanel;
    private JPanel PrioridadPanel;
    private JPanel estadoPanel;
    private JPanel vencimientoPanel;
    private JPanel responsablePanel;
    private JButton crearTareaButton;
    private JTextField descripcionTareatextField;
    private JPanel datePickerPanel;
    private JComboBox prioridadComboBox;
    private JComboBox estadoComboBox;
    private JComboBox responsableComboBox;
    private JDateChooser dateChooser;

    private ProyectoController proyectoController;
    private ProyectoTableModel proyectoTableModel;
    private List<Proyecto> todoProyecto;
    private List<Usuario> todoUsuario;
    private Map<String, String> usuarioMap = new HashMap<>();
    private String UsuarioSeleccionado = " ";
    private TareaController tareaController;
    private TareaTableModel tareaTableModel = new TareaTableModel();


    public MainView(ProyectoTableModel proyectoTableModel , ProyectoController proyectoController,
                    List<Proyecto> proyectos, TareaController tareaController, TareaTableModel tareaTableModel, List<Tarea> tareas)
    {
        setTitle("Sistema Medico");
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        this.proyectoController = proyectoController;
        this.proyectoTableModel = proyectoTableModel;
        this.tareaController = tareaController;
        this.tareaTableModel = tareaTableModel;
        bind(proyectoTableModel,proyectoController, tareaController, tareaTableModel, proyectos, tareas);
        tablaTareas.setVisible(false);
        formTareaPanel.setVisible(false);
        //tablaTareas.setModel(tareaTableModel);
        addListeners();
        //anniadir todos los inits de los comboBox
        initResponsableSelector();
        initEncargadoSelector();
        initDatePickers();
        initEstadoSelector();
        initPrioridadSelector();

        tablaTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaTareas.getSelectedRow();
                    if (fila >= 0) {
                        Tarea tareaSeleccionada = tareaTableModel.getAt(fila);
                        mostrarDialogoEditarTarea(tareaSeleccionada);
                    }
                }
            }
        });
    }

    public void bind(ProyectoTableModel model, ProyectoController proyectoController, TareaController tareaController, TareaTableModel tareaTableModel, List<Proyecto> listaProyectos, List<Tarea> listaTareas) {
        this.proyectoController = proyectoController;
        proyectoTableModel = model;
        tablaProyectos.setModel(model);
//        if (prescripciones != null) model.setRows(prescripciones);
//        if (prescripciones != null && !prescripciones.isEmpty()) {
//            Proyecto p = prescripciones.get(0);
//            tareaTableModel.setRows(p.getListaTareas());
//        }

        this.tareaController=tareaController;
        this.tareaTableModel=tareaTableModel;
        tablaTareas.setModel(tareaTableModel);

        if(listaProyectos!=null){this.proyectoTableModel.setRows(listaProyectos);}
        if (listaTareas!=null){this.tareaTableModel.setRows(listaTareas);}
        descripcionTextField.requestFocus();


    }

    public void getTareas(int proyecto){
        List<Tarea> tareas = tareaController.leePorNumero(proyecto);
        tareaTableModel.setRows(tareas);
        tareaTableModel.fireTableDataChanged();
    }

    public JPanel getContentPanel() {
        return MainPanel;
    }

    public void addListeners(){
        crearProyecto.addActionListener(e ->onAdd() );
        crearTareaButton.addActionListener(e->onAgregarTarea());

        tablaProyectos.getSelectionModel().addListSelectionListener(e -> onTableSelection(e));
        tablaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void onClear() {
        tablaProyectos.clearSelection();
        tablaTareas.clearSelection();
        descripcionTextField.setText("");
        descripcionTextField.requestFocus();
        descripcionTareatextField.setText("");
        descripcionTareatextField.requestFocus();
    }

    private void onAdd(){
/*
        try {
            requireBound();
//no dio chance a hacer el metodo de DatosForm para las tareas...
            DatosForm d = readForm();

            if (d.descripcion.isEmpty() || d.encargadoGeneral == null || d.encargadoGeneral.isBlank()) {
                warn("Debe ingresar una descripción y seleccionar un encargado.");
                return;
            }

            List<Tarea> recetas = getCurrentRecetas(); //obtiene todas las recetas de la tabla
            TareaController.agregar(d.numProyecto,d.descripcion,d.encargadoGeneral, recetas);
            for (Tarea rec : recetas) {
                recetaController.agregar(d.numeroPrescripcion, rec.getCodigo(), rec.getNombre(),
                        rec.getPresentacion(), rec.getCantidad(),
                        rec.getDuracion(), rec.getIndicaciones());
            }
            onClear();
        } catch (Exception ex) {
            showError("Error al agregar: " + ex.getMessage(), ex);
        }
*/

        try {

            String descripcion = descripcionTextField.getText().trim();
            String encargado = (String) encargadoComboBox.getSelectedItem();

            if (descripcion.isEmpty() || encargado == null || encargado.isBlank()) {
                warn("Debe ingresar una descripción y seleccionar un encargado.");
                return;
            }



            List<Tarea> listaTareas = tareaTableModel.getRows();



            proyectoController.agregar(descripcion, encargado, 0, listaTareas);


            onClear();

        } catch (Exception ex) {
            showError("Error al agregar proyecto: " + ex.getMessage(), ex);
        }

    }

    private void onTableSelection(ListSelectionEvent e) {
        // if (e.getValueIsAdjusting()) return;
        if (proyectoTableModel == null) return;
        int row = tablaProyectos.getSelectedRow();
        if (row < 0){
            // limpiar vista de tareas si no hay selección
            tareaTableModel.setRows(new ArrayList<>());
            return;
        }
        Proyecto p = proyectoTableModel.getAt(row);
        if (p == null) {
            tareaTableModel.setRows(new ArrayList<>());
            return;
        }
        tablaTareas.setVisible(true);
        formTareaPanel.setVisible(true);
        getTareas(p.getNumProyecto());
    }

    private void onTableSelectionTarea(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        if (tareaTableModel == null) return;
        int row = tablaTareas.getSelectedRow();
        if (row < 0) return;
        Tarea p = tareaTableModel.getAt(row);
        if (p == null) return;

        descripcionTextField.setText(p.getDescripcion());


    }


    private List<Tarea> getCurrentRecetas() {
        List<Tarea> data = new ArrayList<>();
        for (int i = 0; i < tareaTableModel.getRowCount(); i++) {
            Tarea r = tareaTableModel.getAt(i);
            if (r != null) data.add(r);
        }
        return data;
    }
    private void initEncargadoSelector(){

        UsuarioFileStore store = new UsuarioFileStore(new File("usuarios.xml") );
        todoUsuario = store.readAll();

        encargadoComboBox.removeAllItems();
        for(Usuario p : todoUsuario){
            //if(p.getNombreCompleto() != null){
            // p.getListaRecetas().forEach(receta -> medicamentosUnicos.add(receta.getNombre()));
            //}
            usuarioMap.put(p.getNombre(), p.getId());
            encargadoComboBox.addItem(p.getNombre());
        }
        encargadoComboBox.addActionListener(e -> {
            String seleccionado = (String) encargadoComboBox.getSelectedItem();
            if(seleccionado != null){
                UsuarioSeleccionado = usuarioMap.getOrDefault(seleccionado, " ");
                System.out.println("Usuario seleccionado: " + seleccionado + " (ID: " + UsuarioSeleccionado + ")");
            }
        });

    }
    private void initResponsableSelector(){

        UsuarioFileStore store = new UsuarioFileStore(new File("usuarios.xml") );
        todoUsuario = store.readAll();

        responsableComboBox.removeAllItems();
        for(Usuario p : todoUsuario){
            //if(p.getNombreCompleto() != null){
            // p.getListaRecetas().forEach(receta -> medicamentosUnicos.add(receta.getNombre()));
            //}
            usuarioMap.put(p.getNombre(), p.getId());
            responsableComboBox.addItem(p.getNombre());
        }
        responsableComboBox.addActionListener(e -> {
            String seleccionado = (String) responsableComboBox.getSelectedItem();
            if(seleccionado != null){
                UsuarioSeleccionado = usuarioMap.getOrDefault(seleccionado, " ");
                System.out.println("Usuario seleccionado: " + seleccionado + " (ID: " + UsuarioSeleccionado + ")");
            }
        });

    }
    private void initEstadoSelector(){
        estadoComboBox.removeAllItems();
        for (String s : Arrays.asList("Abierta", "En progreso", "En revisión", "Resuelta")) {
            estadoComboBox.addItem(s);
        }

    }
    private void initPrioridadSelector(){
        prioridadComboBox.removeAllItems();
        for (String s : Arrays.asList("Alta", "Media", "Baja")) {
            prioridadComboBox.addItem(s);
        }
    }
    private void onAgregarTarea(){
        int selectedRow = tablaProyectos.getSelectedRow();
        if (selectedRow < 0) {
            warn("Debe seleccionar un proyecto primero.");
            return;
        }

        Proyecto pro = proyectoTableModel.getAt(selectedRow);
        tareaController.agregar(pro.getNumProyecto(), descripcionTareatextField.getText(), dateChooser.getDate(), (String) prioridadComboBox.getSelectedItem(), (String) estadoComboBox.getSelectedItem(), (String) responsableComboBox.getSelectedItem());
        onClear();

    }
    private void warn(String msg) {
        JOptionPane.showMessageDialog(MainPanel, msg, "Atención", JOptionPane.WARNING_MESSAGE);
    }
    private void initDatePickers() {
        datePickerPanel.setLayout(new BorderLayout());


        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        datePickerPanel.add(dateChooser, BorderLayout.CENTER);



        datePickerPanel.revalidate();
        datePickerPanel.repaint();

    }
    private static class DatosForm {
        String descripcion, encargadoGeneral; int numProyecto;
    }
    private DatosForm readForm() {
        DatosForm d = new DatosForm();
        d.descripcion = descripcionTextField.getText();
        d.encargadoGeneral = (String) encargadoComboBox.getSelectedItem();
        d.numProyecto = 0;
        return d;
    }

    private void requireBound() {
        if (proyectoController == null || proyectoTableModel == null)
            throw new IllegalStateException("ClienteView no está enlazado (bind) a controller/model.");
    }

    private Integer parseInt(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : Integer.parseInt(s.trim()); }
        catch (Exception e) { return null; }
    }
    private int orDefault(Integer v, int def) { return v == null ? def : v; }
    private String safe(String s) { return s == null ? "" : s.trim(); }

    private void showError(String msg, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(MainPanel, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void onModificarTarea() {
        int row = tablaTareas.getSelectedRow();
        if (row < 0) { warn("Seleccione una tarea de la tabla para modificar."); return; }

        Tarea rec = tareaTableModel.getAt(row);
        if (rec == null) return;

        String descripcion = descripcionTareatextField.getText();
        String encargado = (String) responsableComboBox.getSelectedItem();
        String prioridad = (String) prioridadComboBox.getSelectedItem();
        Date fecahvencimiento = dateChooser.getDate();
        String estado = (String)estadoComboBox.getSelectedItem();
        int numero  = 0;

        rec.setNumero(numero);
        rec.setDescripcion(descripcion);
        rec.setEncargado(encargado);
        rec.setPrioridad(prioridad);
        rec.setFechaFinalizacion(fecahvencimiento);
        rec.setEstado(estado);

        tareaTableModel.fireTableRowsUpdated(row, row);

    }

    private void mostrarDialogoEditarTarea(Tarea tareaSeleccionada) {
        if (tareaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Editar Tarea", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblPrioridad = new JLabel("Prioridad:");
        JComboBox<String> comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        comboPrioridad.setSelectedItem(tareaSeleccionada.getPrioridad());

        JLabel lblEstado = new JLabel("Estado:");
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Abierta", "En-progreso", "En-revisión", "Resuelta"});
        comboEstado.setSelectedItem(tareaSeleccionada.getEstado());

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            String nuevaPrioridad = (String) comboPrioridad.getSelectedItem();
            String nuevoEstado = (String) comboEstado.getSelectedItem();

            tareaSeleccionada.setPrioridad(nuevaPrioridad);
            tareaSeleccionada.setEstado(nuevoEstado);

            if (tareaController != null) {
                tareaController.actualizar(tareaSeleccionada.getNumero(), tareaSeleccionada.getProyecto(), tareaSeleccionada.getDescripcion(), tareaSeleccionada.getFechaFinalizacion(),tareaSeleccionada.getPrioridad(),tareaSeleccionada.getEstado(),tareaSeleccionada.getEncargado());
            }

            dialog.dispose();
        });


        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(lblPrioridad);
        dialog.add(comboPrioridad);
        dialog.add(lblEstado);
        dialog.add(comboEstado);
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);

        dialog.setVisible(true);

        actualizarListaTareas();
    }
    private void actualizarListaTareas() {
        tareaTableModel.fireTableDataChanged();
    }

}
