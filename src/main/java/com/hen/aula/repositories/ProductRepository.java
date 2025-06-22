package com.hen.aula.repositories;

import com.hen.aula.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // para criar um repositorio (acesso ao banco de dados), colocamos
    // o nome da entidade e mais o tipo do id da entidade que é long

    //Quando usamos o Pageable a consulta tem que ser   com a JPQL
    @Query("SELECT obj " +
            "FROM Product obj " +
            "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%')) " )
    Page<Product> searchByName(String name, Pageable pageable); /*
    Pageable tem que estar também como aguarmento
    para métodos customizados é bom colocar a palavra search, porque quando
    usamos o queryMethod ele tem métodos padrão que começa com find*/

}
