package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.ProductDTO;
import com.hen.aula.entities.Product;
import com.hen.aula.repositories.ProductRepository;
import com.hen.aula.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/products") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class ProductController {

    @Autowired
    private ProductService service; //Injetando product service
    // aqui ele já cria um construtor automático product para fazer a injeção
    // de dependencia, que ai eu n preciso mudar o service aqui
    // quando quiser mudar o método do service

    @GetMapping(value = "/{id}") //Método GET para retorna um ProductDTO pelo id
    public ProductDTO findById(@PathVariable Long id){ // Long id é o parâmetro que vai ser o id
        // do produto, para colocar parâmetro na rota(ul) tem que colocar o @PathVariable
        // que vai casar com o value do GetMapping
        ProductDTO dto = service.findById(id);
       return dto;

       // para resumir as lihas acima return service.findById(id)
        //como o service já tem o DTO n precisa colocar essa varial
        // DTO do tipo ProductDTO
    }

}
