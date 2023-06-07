package com.example.apidrogueriaspringwebflux.models.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "facturas")
public class Factura {

    public Factura(){

     fechaCreacion=new Date();
    }

    @Id
    private String id;

    private String descripcion;

    private String observacion;

    private List<ItemFactura> itemFacturas;

    private Date fechaCreacion;


    public Double getTotal(){

        Double total=0.0;

        for (ItemFactura item:itemFacturas){

            total+= item.getImporte();
        }

        return total;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<ItemFactura> getItemFacturas() {
        return itemFacturas;
    }

    public void setItemFacturas(List<ItemFactura> itemFacturas) {
        this.itemFacturas = itemFacturas;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
