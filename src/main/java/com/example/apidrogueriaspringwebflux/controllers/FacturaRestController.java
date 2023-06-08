package com.example.apidrogueriaspringwebflux.controllers;

import com.example.apidrogueriaspringwebflux.models.document.Factura;
import com.example.apidrogueriaspringwebflux.models.document.Producto;
import com.example.apidrogueriaspringwebflux.models.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class FacturaRestController {

    @Autowired
    IService service;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> getByid(@PathVariable String id){
        return service.findFacturaById(id)
                .map(factura -> {
                    if (factura == null) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                    return new ResponseEntity<>(factura, HttpStatus.OK);

                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT))
                .onErrorResume(error -> Mono.just(ResponseEntity.internalServerError().build()))
                ;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Factura factura){

        service.saveFactura(factura);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){

        service.deleteFactura(id);
    }
    









}
