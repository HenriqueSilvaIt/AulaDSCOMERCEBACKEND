package com.hen.aula.services;

import com.hen.aula.dto.CategoryDTO;
import com.hen.aula.dto.ProductDTO;
import com.hen.aula.dto.ProductMinDTO;
import com.hen.aula.entities.Category;
import com.hen.aula.entities.Product;
import com.hen.aula.repositories.CategoryRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service // indica para o Spring que essa classe é um serviço
public class CategoryService {

    @Autowired // injeção de depêndência
    private CategoryRepository repository;// instanciando o repositóry
    // do product para pegar informações do produto do banco de dados

        //Operação para buscar todos produtos paginados
        @Transactional(readOnly = true)
        public List<CategoryDTO> findAll() {
            List<Category> result = repository.findAll(); // tem que passar o pageable para o repository
            // porque o repository já tem  findall que recebe o pageable e retorna paginado
            return  result.stream().map(x ->  new CategoryDTO(x)).collect(Collectors.toList()); //to Lit para voltar para lista
            /* ACIMA usando o PAGE chamamos result.map direto sem stream
            porque o pageable já é um string do java
            result.stream().map( x -> new ProductDTO(x)).toList();
            RETURN acima é para cada registro da lista original
            * vamos instanciar um ProductDTO, para trazer os atributos
            * d todos os produtos em formato DTO, tem que colocar
            * o x dentro do PRODUCT.DTO porque x é cada atributo
            * de product sendo inserido no product dto*/
        }


}
