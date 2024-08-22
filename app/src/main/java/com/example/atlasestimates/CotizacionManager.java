package com.example.atlasestimates;

public class CotizacionManager {
    private static CotizacionManager instance = null;
    private Cotizacion cotizacion;

    private CotizacionManager() {
    }

    public static CotizacionManager getInstance() {
        if (instance == null) {
            instance = new CotizacionManager();
        }
        return instance;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Cotizacion getCotizacion() {
        return this.cotizacion;
    }
}
