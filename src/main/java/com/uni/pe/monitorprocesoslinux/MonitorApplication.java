package com.uni.pe.monitorprocesoslinux;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MonitorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Le añadimos una barra "/" al principio de la ruta completa del paquete.
// Esto le dice a Java que busque desde la raíz de los recursos, lo cual es más seguro.
        FXMLLoader fxmlLoader = new FXMLLoader(MonitorApplication.class.getResource("/com/uni/pe/monitorprocesoslinux/monitor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("MONITOR DE PROCESOS DE LINUX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}