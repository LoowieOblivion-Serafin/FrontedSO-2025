package com.uni.pe.monitorprocesoslinux;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClienteSocket {
    private static final File SOCKET_FILE = new File("/tmp/server_therake_socket.sock");
    private final AFUNIXSocket socket;
    private final InputStream in;
    private final OutputStream out;

    public ClienteSocket() throws IOException {
        socket = AFUNIXSocket.newInstance();
        socket.connect(new AFUNIXSocketAddress(SOCKET_FILE));
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public List<Proceso> consultarProcesos() throws IOException {
        List<Proceso> procesos = new ArrayList<>();

        // 1. Enviar comando "send\0"
        out.write("send\0".getBytes(StandardCharsets.UTF_8));
        out.flush();

        // 2. Esperar "START"
        String respuesta = leerMensaje();
        if (!"START".equals(respuesta)) {
            System.err.println("Error: respuesta inesperada: " + respuesta);
            return procesos;
        }

        // 3. Enviar primer OK
        enviarOk();

        // 4. Leer procesos
        while (true) {
            String linea = leerMensaje();
            if ("STOP".equals(linea)) break;

            String[] partes = linea.trim().split("\\s+");
            if (partes.length >= 5) {
                try {
                    int pid = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    String estado = traducirEstado(partes[2].charAt(0));
                    double cpu = Double.parseDouble(partes[3]);
                    double mem = Double.parseDouble(partes[4]);
                    procesos.add(new Proceso(pid, nombre, estado, cpu, mem));
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear línea: " + linea);
                }
            }

            // Confirmar recepción
            enviarOk();
        }

        return procesos;
    }

    private void enviarOk() throws IOException {
        out.write("OK\0".getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    private String leerMensaje() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b == 0) break;
            buffer.write(b);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    public void cerrar() throws IOException {
        socket.close();
    }

    private String traducirEstado(char estado) {
        return switch (estado) {
            case 'R' -> "Running";
            case 'S' -> "Sleeping";
            case 'D' -> "Waiting";
            case 'Z' -> "Zombie";
            case 'T' -> "Stopped";
            case 't' -> "Tracing stop";
            case 'W' -> "Waking";
            case 'X' -> "Dead";
            case 'x' -> "Dead";
            case 'K' -> "Wakekill";
            case 'P' -> "Parked";
            case 'I' -> "Idle";
            default -> "Unknown";
        };
    }
}
