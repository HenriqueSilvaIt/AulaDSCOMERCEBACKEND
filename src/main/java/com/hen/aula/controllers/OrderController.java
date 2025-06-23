package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.OrderDTO;
import com.hen.aula.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController // Quando aplicação rodar
// essa classe vai estar na web
@RequestMapping(value = "/orders") // configura essa classe
// para que ela responsa via web nessa rota e nome da rota
public class OrderController {

@Autowired
private OrderService service; //Injetando product service
// aqui ele já cria um construtor automático product para fazer a injeção
// de dependencia, que ai eu n preciso mudar o service aqui
// quando quiser mudar o método do service

    /*Método, Pega o id repassa para o service e pega o order DTO */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@GetMapping(value = "/{id}") //Método GET para retorna um ProductDTO pelo id
public ResponseEntity<OrderDTO> findById(@PathVariable Long id){ // Long id é o parâmetro que vai ser o id
// do produto, para colocar parâmetro na rota(ul) tem que colocar o @PathVariable
// que vai casar com o value do GetMapping
OrderDTO dto = service.findById(id);
return ResponseEntity.ok(dto);// estamos dizendo que vai retornar o response
// entity dto, mesagem ok  http 200 que de certo,  onde o corpo vai ser o dto
//  dto = service.insert(dto);
//  return dto;

// para resumir as lihas acima return service.findById(id)
//como o service já tem o DTO n precisa colocar essa varial
// DTO do tipo ProductDTO
}

}
