module com.uni.pe.monitorprocesoslinux {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.uni.pe.monitorprocesoslinux to javafx.fxml;
    exports com.uni.pe.monitorprocesoslinux;
}