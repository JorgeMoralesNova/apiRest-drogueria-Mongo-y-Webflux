package com.example.apidrogueriaspringwebflux.models.service;

import com.example.apidrogueriaspringwebflux.models.dao.IFacturaDao;
import com.example.apidrogueriaspringwebflux.models.dao.IProductoDao;
import com.example.apidrogueriaspringwebflux.models.document.Factura;
import com.example.apidrogueriaspringwebflux.models.document.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IServiceImpl implements IService{

    @Autowired
    private IProductoDao productoDao;
    @Autowired
    private IFacturaDao facturaDao;


    @Override
    public Flux<Producto> listarProductos() {
        return productoDao.findAll();
    }

    @Override
    public Mono<Producto> findProductoByID(String id) {
        return productoDao.findById(id);
    }

    @Override
    public Mono<Producto> findProductoByNombre(String nombre) {
        return productoDao.findProductoByNombreLikeIgnoreCase(nombre);
    }

    @Override
    public Flux<Producto> findProductosByPrecio(Integer precio) {
        return productoDao.findProductoByPrecio(precio);
    }


    @Override
    public Mono<Producto> saveProducto(Producto producto) {

        return productoDao.save(producto);

    }

    @Override
    public Mono<Producto> actualizar(String id, Producto producto) {
        return findProductoByID(id)
                .flatMap(produc -> {
                    produc.setNombre(producto.getNombre());
                    produc.setDescripcion(producto.getDescripcion());
                    produc.setPrecio(producto.getPrecio());
                    produc.setFecha_vencimiento(producto.getFecha_vencimiento());
                    return productoDao.save(produc);
                });
    }

    @Override
    public void deleteProducto(String id) {
        productoDao.deleteById(id);
    }

    @Override
    public void deleteProductoByNombre(String nombre) {
        productoDao.deleteProductoByNombre(nombre);
    }

    @Override
    public Flux<Producto> productos_a_vencer() {
        Date actual= new Date();

        Flux<Producto> productos=listarProductos();

        List<Producto> productos_a_vencer=new ArrayList<>();

        productos.subscribe(producto -> {

            Date fechaVencimiento=producto.getFecha_vencimiento();

            if (fechaVencimiento != null) {
                long tiempoRestante = fechaVencimiento.getTime() - actual.getTime();
                long tiempoMaximoUnAnio = 365L * 24 * 60 * 60 * 1000; // 1 año en milisegundos

                if (tiempoRestante <= 0 || tiempoRestante <= tiempoMaximoUnAnio) {
                    productos_a_vencer.add(producto);
                }
            }

        });

        return Flux.fromIterable(productos_a_vencer);

    }

    @Override
    public Mono<Factura> findFacturaById(String id) {
        return facturaDao.findById(id);
    }

    @Override
    public Flux<Factura> findFacturaByFechaCreacion(Date fecha) {
        return facturaDao.findFacturaByFechaCreacion(fecha);
    }

    @Override
    public void saveFactura(Factura factura) {

        facturaDao.save(factura);
    }

    @Override
    public void deleteFactura(String id) {

        facturaDao.deleteById(id);
    }
}
