package com.example.apidrogueriaspringwebflux.models.dao;

import com.example.apidrogueriaspringwebflux.models.document.Factura;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Date;

public interface IFacturaDao extends ReactiveMongoRepository<Factura,String> {

    @Transactional(readOnly = true)
    Flux<Factura> findFacturaByFechaCreacion(Date fechaCreacion);


}
