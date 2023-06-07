package com.example.apidrogueriaspringwebflux.models.dao;

import com.example.apidrogueriaspringwebflux.models.document.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoDao extends ReactiveMongoRepository<Producto, String> {

    @Transactional(readOnly = true)
    Mono<Producto> findProductoByNombreLikeIgnoreCase(String nombre);

    @Transactional(readOnly = true)
    Flux<Producto> findProductoByPrecio(Integer precio);

    @Transactional
    void deleteProductoByNombre(String nombre);

}
