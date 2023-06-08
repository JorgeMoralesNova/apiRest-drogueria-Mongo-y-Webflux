package com.example.apidrogueriaspringwebflux.controllers;

import com.example.apidrogueriaspringwebflux.models.dao.IProductoDao;
import com.example.apidrogueriaspringwebflux.models.document.Producto;
import com.example.apidrogueriaspringwebflux.models.service.IService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/apiRest/catalogo")
@RestController
public class ProductoRestController {

    private static final Logger log=LoggerFactory.getLogger(SpringApplication.class);


    @Autowired
    IService service;

    @GetMapping
    public Mono<ResponseEntity<?>> getAll() {
        return service.listarProductos()
                .collectList()
                .flatMap(productos -> {
                    if (productos.isEmpty()) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(productos));
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> getProductoByid(@PathVariable String id) {
        return service.findProductoByID(id)
                .map(producto -> {
                    if (producto == null) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                    return new ResponseEntity<>(producto, HttpStatus.OK);

                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT))
                .onErrorResume(error -> Mono.just(ResponseEntity.internalServerError().build()))
                ;

    }


    @GetMapping("/nombre/{nombre}")
    public Mono<ResponseEntity<?>> getProductobyName(@PathVariable String nombre) {

        return service.findProductoByNombre(nombre)
                .map(producto -> {
                            if (producto == null) {
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                    return new ResponseEntity<>(producto, HttpStatus.OK);
                        }
                ).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT))
                .onErrorResume(error -> Mono.just(ResponseEntity.internalServerError().build()))
                ;

    }

    @GetMapping("/precio/{precio}")
    public Mono<ResponseEntity<?>> getByPrecio(@PathVariable Integer precio) {

        return service.findProductosByPrecio(precio)
                .collectList()
                .map(producto -> {
                    if (producto.isEmpty()) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }

                    return new ResponseEntity<>(producto, HttpStatus.OK);
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.internalServerError().build()))
                ;

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/productos_a_vencer")
    public Flux<Producto> productosAvencer(){

        return service.productos_a_vencer();
    }



    @PostMapping()
    public Mono<ResponseEntity<Producto>> createProducto(@Valid @RequestBody Mono<Producto> producto) {
        return producto.flatMap(p -> service.saveProducto(p))
                .map(savedProducto -> ResponseEntity.status(HttpStatus.CREATED).body(savedProducto))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> update(@PathVariable String id, @RequestBody Producto producto) {
        return service.actualizar(id, producto)
                .map(updatedProducto -> ResponseEntity.ok(updatedProducto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){

        service.deleteProducto(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/nombre/{nombre}")
    public void deleteByNombre(@PathVariable String nombre){

        service.deleteProducto(nombre);
    }

}