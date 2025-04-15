package com.hen.aula.controllers;


 //Vamos configurar a classe para que ela responda pela classe / producs


import com.hen.aula.dto.CustomError;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.entities.Product;
import com.hen.aula.repositories.ProductRepository;
import com.hen.aula.services.ProductService;
import com.hen.aula.services.execeptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){ // Long id é o parâmetro que vai ser o id
        // do produto, para colocar parâmetro na rota(ul) tem que colocar o @PathVariable
        // que vai casar com o value do GetMapping
         ProductDTO dto = service.findById(id);
       return ResponseEntity.ok(dto);// estamos dizendo que vai retornar o response
        // entity dto, mesagem ok  http 200 que de certo,  onde o corpo vai ser o dto
        //  dto = service.insert(dto);
        //  return dto;

       // para resumir as lihas acima return service.findById(id)
        //como o service já tem o DTO n precisa colocar essa varial
        // DTO do tipo ProductDTO
    }


    // Buscando todos os produtos páginados
    @GetMapping
    // quando é paginado tem que ser Page não list
    // quando colocamos o ResponseEntity nós estamos dizendo
    // que o método vai retornar um response entity
    // onde corpo da resposta vai ser o dto
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) { // Pageable é do data.domain do spring para paginar páginas
        Page<ProductDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto); //

       // return service.findAll(pageable);
    }

    //Inserindo produto na tabela
    @PostMapping
    // quando colocamos o ResponseEntity nós estamos dizendo
    // que o método vai retornar um response entity
    // onde corpo da resposta vai ser o dto
    public ResponseEntity<ProductDTO> insert (@Valid @RequestBody ProductDTO dto) {
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

    //Atualizando Produto
    @PutMapping(value="/{id}")// a rota ul do PUT esper o id
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        //PathVariable é para que esse parmaetro do método Long id, seja passado
        // na url como argumento e o RequestBody é para que o produto seja passado
        // no corpo da requisição

        // só vai funcionar se colocar o @Valid no método que tem validação no controller
        // tem que ser antes do @RequestBody do Dto, sempre do dto

        dto = service.update(id, dto); // atualiza o id e o produtoDto fornecido n URL

        return ResponseEntity.ok(dto); // ok para retornar 200 http
    }

    //Deletando produto pelo id
    @DeleteMapping(value = "/{id}")
    // o Void no responseEntity quer dizer que ele n vai retornar nada
    // só deletar
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // PathVariable id é para indicar na ul qual produto vamos deletar
        service.delete(id);
        return ResponseEntity.noContent().build();
        // o noContent retorna o 204 que é para dizer que deu sucesso na requisição
        // porém não retorna nada esse método, e o .build é para ele
        // construtir certinho  o ResponseEntity

 /*
    IMPORTANTE: se vocÊ tentar apagar um produto que faz parte de um pedido(se n tabela do pedido ficaria
    com o produto fantasma)  por padrão no JPA(banco de dados) vai dar falha de integridade referencial
    */
        /*    Importante: se tentar Apagar um produto de um ID inexistente o SPRING versão atual não da mais erro 500,  ele continua retornar 204 como se fosse deletado com sucesso,
    mas nesse caso o certo é ter uma exceção*/
    }
}
