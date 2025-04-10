package com.hen.aula.repositories;

import com.hen.aula.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // para criar um repositorio (acesso ao banco de dados), colocamos
    // o nome da entidade e mais o tipo do id da entidade que é long


}
