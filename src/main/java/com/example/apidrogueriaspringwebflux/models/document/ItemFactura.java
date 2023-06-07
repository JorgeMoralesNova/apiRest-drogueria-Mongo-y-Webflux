package com.example.apidrogueriaspringwebflux.models.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(collection = "items_factura")
public class ItemFactura implements Serializable {

    @Id
    private String id;

    private Producto producto;

    private Integer cantidad;


    public Double getImporte(){

        return (producto.getPrecio().doubleValue()*cantidad.doubleValue());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
