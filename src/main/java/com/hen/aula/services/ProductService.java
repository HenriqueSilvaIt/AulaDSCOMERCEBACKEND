package com.hen.aula.services;

import com.hen.aula.entities.Product;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service // indica para o Spring que essa classe é um serviço
public class ProductService {

    @Autowired // injeção de depêndência
    private ProductRepository repository;// instanciando o repositóry
    // do product para pegar informações do produto do banco de dados


        @Transactional(readOnly = true) /*aqui é para dizer
        que é uma operação somente de leitura, como só vamos fazer leitura
        o processamento fica mais rápido*/
        //Busca o produto pelo id no banco de dados
        //e retorna no formato DTO
        public ProductDTO findById(Long id) {

            //Vai no banco de dados do product e retorna o produto que tem esse id
            Optional<Product> result = repository.findById(id);
            Product product = result.get(); // pega o produto  retornado na operação acima
            //Instanciando o ProductDTO, já copiando o resultado do produto
            // acima que pega o produto pelo id, ai no caso só vai trazer
            // as informações do DTO
            ProductDTO dto  = new ProductDTO(product);
             return dto; // retorno o produto somento em DTO

            //Forma resumida do código acim

            /*Product product = repository.findById(id).get() //  consulta o produto no banco pelo id
            * da o get para pegar o produto
            * return new ProductDTO(product) atribui o resultado para  DTO
            * e  o resultado só fica com os campos do DTO*/
        }

}
