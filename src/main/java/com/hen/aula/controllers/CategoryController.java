package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.CategoryDTO;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.dto.ProductMinDTO;
import com.hen.aula.services.CategoryService;
import com.hen.aula.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/categories") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class CategoryController {

@Autowired
private CategoryService service; //Injetando product service
// aqui ele já cria um construtor automático product para fazer a injeção
// de dependencia, que ai eu n preciso mudar o service aqui
// quando quiser mudar o método do service


// Buscando todos os produtos páginados
@GetMapping
// quando é paginado tem que ser Page não list
// quando colocamos o ResponseEntity nós estamos dizendo
// que o método vai retornar um response entity
// onde corpo da resposta vai ser o dto
public ResponseEntity<List<CategoryDTO>> findAll() { // Pageable é do data.domain do spring para paginar páginas
List<CategoryDTO> dto = service.findAll();
return ResponseEntity.ok(dto); //

// return service.findAll(pageable);
}

}
