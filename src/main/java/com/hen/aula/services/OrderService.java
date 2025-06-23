package com.hen.aula.services;

import com.hen.aula.dto.CategoryDTO;
import com.hen.aula.dto.OrderDTO;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.dto.ProductMinDTO;
import com.hen.aula.entities.Category;
import com.hen.aula.entities.Order;
import com.hen.aula.entities.Product;
import com.hen.aula.repositories.OrderRepository;
import com.hen.aula.repositories.ProductRepository;
import com.hen.aula.services.execeptions.DataBaseException;
import com.hen.aula.services.execeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service // indica para o Spring que essa classe é um serviço
public class OrderService {

    @Autowired // injeção de depêndência
    private OrderRepository repository;// instanciando o repositóry
    // do product para pegar informações do produto do banco de dados

        /*Método encontra o order no banco de dados pelo id e e retorna o objeto
        *    order convertendo para DTO no finl*/
        @Transactional(readOnly = true) /*aqui é para dizer
        que é uma operação somente de leitura, como só vamos fazer leitura
        o processamento fica mais rápido*/
        //Busca o produto pelo id no banco de dados
        //e retorna no formato DTO
        public OrderDTO findById(Long id) {

                //Vai no banco de dados do product e retorna o produto que tem esse id
                Order order  = repository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Recurso não encontrado")); // orElseThrow
            // pega execeção automatica do findById, porque o findById é um optional, pode
            // retornar um id inexistente que é uma execeção, e ele pega automatico
            // não precisa criar uma execeção personalizada para ele
            //Optional<Product> result = repository.findById(id);
               // Product product = result.get(); // pega o produto  retornado na operação acima
                //Instanciando o ProductDTO, já copiando o resultado do produto
                // acima que pega o produto pelo id, ai no caso só vai trazer
                // as informações do DTO
                OrderDTO dto = new OrderDTO(order);
                return dto; // retorno o produto somento em DTO
                //Forma resumida do código acim


            /*Product product = repository.findById(id).get() //  consulta o produto no banco pelo id
            * da o get para pegar o produto
            * return new ProductDTO(product) atribui o resultado para  DTO
            * e  o resultado só fica com os campos do DTO*/
        }

}
