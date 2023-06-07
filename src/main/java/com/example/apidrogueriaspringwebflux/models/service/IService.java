package com.example.apidrogueriaspringwebflux.models.service;

import com.example.apidrogueriaspringwebflux.models.document.Factura;
import com.example.apidrogueriaspringwebflux.models.document.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface IService {

    Flux<Producto> listarProductos();

    Mono<Producto> findProductoByID(String id);

    Mono<Producto> findProductoByNombre(String nombre);

    Flux<Producto> findProductosByPrecio(Integer precio);

    void saveProducto(Producto producto);

    void actualizar(String id, Producto producto);

    void deleteProducto(String id);

    void deleteProductoByNombre(String nombre);


    Flux<Producto> productos_a_vencer();

    Mono<Factura> findFacturaById(String id);

    Flux<Factura> findFacturaByFechaCreacion(Date fecha);


    void saveFactura(Factura factura);

    void deleteFactura(Factura factura);





}
