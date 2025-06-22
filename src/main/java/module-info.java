module com.uni.pe.monitorprocesoslinux {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.newsclub.net.unix;


    opens com.uni.pe.monitorprocesoslinux to javafx.fxml;
    exports com.uni.pe.monitorprocesoslinux;
}