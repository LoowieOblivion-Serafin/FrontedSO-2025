package com.uni.pe.monitorprocesoslinux;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MonitorController {
    // 1. Enlazamos los componentes del FXML con variables Java.
    // La anotación @FXML es la que hace la magia. El nombre de la variable
    // DEBE coincidir con el fx:id que pusimos en el archivo FXML.
    @FXML
    private TableView<Proceso> tablaProcesos;
    @FXML
    private TableColumn<Proceso, Integer> columnaPID;
    @FXML
    private TableColumn<Proceso, String> columnaNombre;
    @FXML
    private TableColumn<Proceso, String> columnaUsuario;
    @FXML
    private TableColumn<Proceso, String> columnaEstado;
    @FXML
    private TableColumn<Proceso, Double> columnaCPU;
    @FXML
    private TableColumn<Proceso, Double> columnaMemoria;
    @FXML
    private Label etiquetaEstado;

    // Esta es la lista "observable" que contendrá los datos.
    // La TableView la "observará" y se actualizará sola cuando esta lista cambie.
    private ObservableList<Proceso> listaDeProcesos;

    /**
     * El método initialize() es llamado automáticamente por JavaFX
     * después de que el archivo FXML ha sido cargado.
     * Es el lugar perfecto para configurar nuestra tabla y cargar datos iniciales.
     */
    @FXML
    public void initialize() {
        // 2. Configuramos cada columna para que sepa de dónde sacar los datos.
        // El string "pid", "nombre", etc., DEBE coincidir exactamente con el nombre
        // de la propiedad en la clase Proceso.java (ej. pidProperty() -> "pid").
        columnaPID.setCellValueFactory(new PropertyValueFactory<>("pid"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        columnaCPU.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        columnaMemoria.setCellValueFactory(new PropertyValueFactory<>("memoria"));

        // 3. Creamos la lista y la enlazamos con la tabla.
        listaDeProcesos = FXCollections.observableArrayList();
        tablaProcesos.setItems(listaDeProcesos);

        // 4. Cargamos datos de prueba para verificar que todo funciona.
        // Más adelante, esto será reemplazado por los datos del socket.
        cargarDatosDePrueba();

        etiquetaEstado.setText("Procesos: " + listaDeProcesos.size());
    }

    private void cargarDatosDePrueba() {
        listaDeProcesos.add(new Proceso(1001, "firefox", "alvaro", "Running", 15.5, 520.8));
        listaDeProcesos.add(new Proceso(1002, "java", "alvaro", "Running", 8.2, 1024.5));
        listaDeProcesos.add(new Proceso(1003, "systemd", "root", "Sleeping", 0.1, 50.2));
        listaDeProcesos.add(new Proceso(1004, "code", "alvaro", "Sleeping", 5.7, 850.0));
    }
}