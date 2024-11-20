package com.example.atlasestimates;

public class Notificacion {
    private String mensaje;
    private String fecha;

    // Constructor que acepta mensaje y fecha
    public Notificacion(String mensaje, String fecha) {
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
