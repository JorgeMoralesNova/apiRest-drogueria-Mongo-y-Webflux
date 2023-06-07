package com.example.apidrogueriaspringwebflux.models.document;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;


@Document(collection = "productos")
public class Producto implements Serializable {


    public Producto() {
    }

    public Producto(String nombre, Integer precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, Integer precio, String descripcion, Date fecha_vencimiento) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fecha_vencimiento = fecha_vencimiento;
    }

    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty
    @Size(min = 3)
    private String nombre;

    @NotNull
    private Integer precio;

    private String descripcion;


    private Date fecha_vencimiento;


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }





    private static final long serialVersionUID = 42L;

}

