package com.example.atlasestimates;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {
    private Map<String, Double> productPrices;

    public ProductManager() {
        productPrices = new HashMap<>();
        // Inicializa los precios para cada producto
        productPrices.put("Cerco prefabricado", 1000.00);
        productPrices.put("CP. Estandar", 1500.00);
        productPrices.put("CP. Cabeza caballo", 1800.00);
        productPrices.put("Maquinaria Pesada", 5000.00);
        productPrices.put("Poste concreto", 200.00);
        productPrices.put("Block concreto", 50.00);
        productPrices.put("Desarrollo projectos", 3000.00);
        productPrices.put("Srv. Topografia", 2500.00);
    }

    public Double getPrice(String productName) {
        return productPrices.getOrDefault(productName, 0.0);
    }

    public void updatePrice(String productName, Double price) {
        productPrices.put(productName, price);
    }
}