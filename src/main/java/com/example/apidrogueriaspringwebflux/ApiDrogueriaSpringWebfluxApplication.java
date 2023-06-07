package com.example.apidrogueriaspringwebflux;

import com.example.apidrogueriaspringwebflux.models.dao.IProductoDao;
import com.example.apidrogueriaspringwebflux.models.document.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ApiDrogueriaSpringWebfluxApplication implements CommandLineRunner {

    private static final Logger log= LoggerFactory.getLogger(SpringApplication.class);

    @Autowired
    private ReactiveMongoTemplate drop;

    @Autowired
    private IProductoDao productoDao;

    public static void main(String[] args) {
        SpringApplication.run(ApiDrogueriaSpringWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        drop.dropCollection("productos").subscribe();

        Flux<Producto> productos= Flux.just(new Producto("ibuprofeno", 3000),
                new Producto("acetaminofen", 1200));


        productos.doOnNext(producto -> {log.info(producto.getNombre().concat(" ".concat(producto.getPrecio().toString())));}).repeat(3)
                .flatMap(producto -> productoDao.save(producto)).subscribe(producto-> log.info("insert " + producto.getNombre()) );



    }
}
