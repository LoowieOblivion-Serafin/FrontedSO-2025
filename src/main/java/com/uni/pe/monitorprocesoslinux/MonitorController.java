package com.uni.pe.monitorprocesoslinux;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.IOException;

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
    private ClienteSocket cliente;

    /**
     * El met-odo itialize() es llamado automáticamente por JavaFX
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
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        columnaCPU.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        columnaMemoria.setCellValueFactory(new PropertyValueFactory<>("memoria"));

        // 3. Creamos la lista y la enlazamos con la tabla.
        listaDeProcesos = FXCollections.observableArrayList();
        tablaProcesos.setItems(listaDeProcesos);

        try {
            cliente = new ClienteSocket();
        } catch (IOException e) {
            etiquetaEstado.setText("Error al conectar con el servidor.");
            e.printStackTrace();
            return;
        }

        cargarDesdeSocket();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> cargarDesdeSocket()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    // cargar los datos en segundo plano desde el socket
    private void cargarDesdeSocket() {
        new Thread(() -> {
            try {
                var procesos = cliente.consultarProcesos();
                Platform.runLater(() -> {
                    listaDeProcesos.setAll(procesos);
                    etiquetaEstado.setText("Procesos: " + procesos.size());
                });
            } catch (IOException e) {
                Platform.runLater(() -> etiquetaEstado.setText("Error al leer del servidor."));
                e.printStackTrace();
            }
        }).start();
    }

    public ClienteSocket getCliente() {
        return cliente;
    }
}