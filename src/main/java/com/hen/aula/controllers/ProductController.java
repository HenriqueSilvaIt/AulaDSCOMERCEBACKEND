package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.entities.Product;
import com.hen.aula.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/products") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping //Método GET
    public String test(){
       Optional<Product> result = repository.findById(1L);
       Product product = result.get();
       return product.getName();
    }

}
