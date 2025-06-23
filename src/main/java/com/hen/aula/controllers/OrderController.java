package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.OrderDTO;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
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


    @PreAuthorize("hasRole('ROLE_OPERATOR')") /*Essa anotation pre authorize permite você apontar qual o perfil de usuário
está liberado para acessar específico end-point, has hole, pega um único perfil de usuário e has any role, pega uma lista de perfis e caso usuário tenha
alguns do perfis está autorizado acessar o recurso*/
    //Inserindo produto na tabela
    @PostMapping
    // quando colocamos o ResponseEntity nós estamos dizendo
    // que o método vai retornar um response entity
    // onde corpo da resposta vai ser o dto
    public ResponseEntity<OrderDTO> insert (@Valid @RequestBody OrderDTO dto) {
        // Ele tem o parâmetro ProductDTO que é o objeto
        // que vai ser declarado no JSON (que esperamos receber no JSON)
        // e para que possa colocar um objeto no corpo da requisão JSON
        // tem que ter o @ RequestBody(responsável por dizer que esse parâmetro
        // ProductDo dto vai ser o corpo da requisição
        dto = service.insert(dto);
        // No cabeçalho da resposta vai ter o link para o recurso craido
        // que é essa URI que criamos abaixo
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") // esse path é para mostrar
                // no Header da requisição o link ul do recurso/ produto que foi criado
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto); // estamos dizendo que vai retornar o response
        // entity dto, mesagem created que  retorna 201 que é criação,
        // nesse  caso tem que instanciar uma URI porque o created recebe uma URI

        //  return dto;

        // se quiser fazer direto é return service.insert(dto)
    }


}
