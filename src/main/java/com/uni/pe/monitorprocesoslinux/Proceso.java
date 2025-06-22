package com.uni.pe.monitorprocesoslinux;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Proceso {
    private final SimpleIntegerProperty pid;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty usuario;
    private final SimpleStringProperty estado;
    private final SimpleDoubleProperty cpu;
    private final SimpleDoubleProperty memoria;

    public Proceso(int pid, String nombre, String usuario, String estado, double cpu, double memoria) {
        this.pid = new SimpleIntegerProperty(pid);
        this.nombre = new SimpleStringProperty(nombre);
        this.usuario = new SimpleStringProperty(usuario);
        this.estado = new SimpleStringProperty(estado);
        this.cpu = new SimpleDoubleProperty(cpu);
        this.memoria = new SimpleDoubleProperty(memoria);
    }

    // --- Getters estándar ---
    // La TableView los usará internamente a través de los "property getters".
    public int getPid() { return pid.get(); }
    public String getNombre() { return nombre.get(); }
    public String getUsuario() { return usuario.get(); }
    public String getEstado() { return estado.get(); }
    public double getCpu() { return cpu.get(); }
    public double getMemoria() { return memoria.get(); }

    // --- Property Getters ---
    // Estos son los métodos que la TableView realmente usa para "observar" los cambios.
    public SimpleIntegerProperty pidProperty() { return pid; }
    public SimpleStringProperty nombreProperty() { return nombre; }
    public SimpleStringProperty usuarioProperty() { return usuario; }
    public SimpleStringProperty estadoProperty() { return estado; }
    public SimpleDoubleProperty cpuProperty() { return cpu; }
    public SimpleDoubleProperty memoriaProperty() { return memoria; }
}
