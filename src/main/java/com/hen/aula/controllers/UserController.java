package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.ProductDTO;
import com.hen.aula.dto.UserDTO;
import com.hen.aula.services.ProductService;
import com.hen.aula.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/users") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class UserController {

@Autowired
private UserService service; //Injetando product service
// aqui ele já cria um construtor automático product para fazer a injeção
// de dependencia, que ai eu n preciso mudar o service aqui
// quando quiser mudar o método do service

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    @GetMapping(value = "/me") /*Método GET para retorna um User, n colocamos entre chave, porque é uma palavra
               me é uma palavra fixa e n uma váriavel igual o id que muda sempre*/
    public ResponseEntity<UserDTO> getMe(){ // Long id é o parâmetro que vai ser o id
    // do produto, para colocar parâmetro na rota(ul) tem que colocar o @PathVariable
    // que vai casar com o value do GetMapping
    UserDTO dto = service.getMe();
    return ResponseEntity.ok(dto);// estamos dizendo que vai retornar o response
    // entity dto, mesagem ok  http 200 que de certo,  onde o corpo vai ser o dto
    //  dto = service.insert(dto);
    //  return dto;

    // para resumir as lihas acima return service.findById(id)
    //como o service já tem o DTO n precisa colocar essa varial
    // DTO do tipo ProductDTO
    }

}
