package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/products") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class ProductController {

    @GetMapping //Método GET
    public String test(){
        return "jes";
    }

}
