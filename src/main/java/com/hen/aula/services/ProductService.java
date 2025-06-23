package com.hen.aula.services;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.hen.aula.dto.CategoryDTO;
import com.hen.aula.dto.ProductMinDTO;
import com.hen.aula.entities.Category;
import com.hen.aula.entities.Product;
import com.hen.aula.dto.ProductDTO;
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
                Product product  = repository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Recurso não encontrado")); // orElseThrow
            // pega execeção automatica do findById, porque o findById é um optional, pode
            // retornar um id inexistente que é uma execeção, e ele pega automatico
            // não precisa criar uma execeção personalizada para ele
            //Optional<Product> result = repository.findById(id);
               // Product product = result.get(); // pega o produto  retornado na operação acima
                //Instanciando o ProductDTO, já copiando o resultado do produto
                // acima que pega o produto pelo id, ai no caso só vai trazer
                // as informações do DTO
                ProductDTO dto = new ProductDTO(product);
                return dto; // retorno o produto somento em DTO
                //Forma resumida do código acim


            /*Product product = repository.findById(id).get() //  consulta o produto no banco pelo id
            * da o get para pegar o produto
            * return new ProductDTO(product) atribui o resultado para  DTO
            * e  o resultado só fica com os campos do DTO*/
        }

        //Operação para buscar todos produtos paginados
        @Transactional(readOnly = true)
        public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
            Page<Product> result = repository.searchByName(name, pageable); // tem que passar o pageable para o repository
            // porque o repository já tem  findall que recebe o pageable e retorna paginado
            return  result.map(x ->  new ProductMinDTO(x)); //to Lit para voltar para lista
            /* ACIMA usando o PAGE chamamos result.map direto sem stream
            porque o pageable já é um string do java
            result.stream().map( x -> new ProductDTO(x)).toList();
            RETURN acima é para cada registro da lista original
            * vamos instanciar um ProductDTO, para trazer os atributos
            * d todos os produtos em formato DTO, tem que colocar
            * o x dentro do PRODUCT.DTO porque x é cada atributo
            * de product sendo inserido no product dto*/
        }

        //FUnção para salvar um produto dt no banco de dados
        @Transactional // como não é só leitura não pode  ter o read Onlu
        public ProductDTO insert(ProductDTO dto) { // pegar os dados do PRODUCTDTO e salvar
            // e salvar no banco de dados
            Product entity = new Product();
            //Copiando as informações do ProductDTO que é  o parâmetro do Método
            // para entidade Product
            // vamos criar o método copyENtitytoDto para copiar dados de um
            // DTO para um produto
            copyDtoToEntity(dto, entity);
            // Salvar novo produto  no banco com as informações do DTO
            entity = repository.save(entity);
            // retorna um DTO(para converte para DTO novamente

            //RESUMO:
            // Intanciamos o objeto product, copiamos os atributos do objeto ProductDTO
            // salvamos o objeto Product atualizado com as informações do DTO
            // e retornamos abaixo o objeto salvoa e atualizado
            return new ProductDTO(entity);
        }

        @Transactional // como não é leitura, não tem o only rea
        public ProductDTO update (Long id, ProductDTO dto) {
            try {
                /*Esse método recebe 2 argumentos como parâmetro
                 *1°  é o id que vai estar na url e os detalhes do produto que vai
                 *estar no corpo da requisição*/

                //Aqui  não vamos criar um novo produto com o new ProductDTO
                // nós vamos apenas instanciar um produto com o id do produto
                // na qualquer queremos atualizar

                // Instanciando produto com a referencia do Id (prepara objeto, n cria
                // nem consulta)
                Product entity = repository.getReferenceById(id);//essa ação
                // getReferenceById do repository(JPA) não vai no banco de dados
                // ao contrário do findById da JPA repository que vai no banco de dados
                // e busca o produto, esse getReferenceById ele só prepara o objeto
                // monitorado pela JPA, diferente quando você faz o new Product
                // diferente também do new ProductDto que é um produto novo
                // que não salvamos  ainda e nem buscamos no banco de dados
                // por isso nesse caso do new ProductDTO temos que fazer
                // responsitory.save(product) para salvar o produto no banco de dados

                // Copiando dados do DTO para o produto que foi instanciado acima com a referencia do Id
                // obs: aqui quanto no método POST só tem que ser os atributos
                // que existem no DTO e não todos os atributos do produto
                // vamos criar o método copyENtitytoDto para copiar dados de um
                // DTO para um produto

                copyDtoToEntity(dto, entity);

            /*entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setImgUrl(dto.getImgUrl());*/

                // salvando produto instanciado no banco de dados
                entity = repository.save(entity);
                // retornando produto atualizado
                return new ProductDTO(entity);
            }
            catch (EntityNotFoundException e ) {
                throw new ResourceNotFoundException("Recurso não encontrado");
            }
        }

        //Deletando produto do banco de dados pelo Id
        @Transactional(propagation = Propagation.SUPPORTS) /* Esse parâmetro
         propagation, faz ele só executar a transação se esse método
         estiver no contexto de outra transação, se não estiver ele
         n vai envolver com o transaction e vai capturar corretamente a execeção do banco de dados
         isso precisa pois se trata de uma exceção a nível de banco de dados
         e não da aplicação em si*/
        // O método vai ser void porque ele só  deleta não retorna nada
        // e vai receber um id como argumento para dizer qual produto
        // será deletado

        /*Exceção do delete by id ( Hoje o spring não gera mais exceção se você tentar deletar um id que não existe , antes gerava)
como não gera mais, primeiro vamos fazer um If para testar se o id existe ou não, se não existir nós geramos a execeção personalizada
vamos utilizar a função do JPA repository.existsById(id) que recebe o id do método como parâmetro para checar se o id existe*/
        public void delete(Long id) {
        if (!repository.existsById(id)) {
            // se o id não existir, lança execeção abaixo
            throw new ResourceNotFoundException("Recurso não encontrado");
        } try {
            // se existir deleta
            repository.deleteById(id);// só passar o id que essa função
            // do deleteById do Jpa deleta no banco de dado
        } catch (DataIntegrityViolationException e) {
                throw new DataBaseException("Falha na integridade referencial");
            }

        }

        // método void para copiar os dados do DTO para entidade produto
        public void copyDtoToEntity(ProductDTO dto, Product entity) {

            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setImgUrl(dto.getImgUrl());

            /*Copia as categoria da entidade para o DTO*/

            entity.getCagetory().clear(); /*antes de atualizar ou inserir uma nova
            categoria no produto, eu preciso limpar a  lista de categoria e
            deixar só as novas categoria atualizada ou inserida*/
            for (CategoryDTO catDTo : dto.getCategories()) {
                /*Para cada categoria DTO acima vamos instanciar
                * uma categoria entity*/
                Category cat = new Category();

                /* e vamos fazer a entidade acima pegar os dados do DTO*/
                cat.setId(catDTo.getId()); /*Estamos copiando que chegou no DTO (pela requisição para uma categoria
                * entidade para categoria entidade que criamos com o New acima*/

                /*Agora vamos copiar a categoria entidade, para a entidade produto*/
                entity.getCagetory().add(cat); /*inserindo
                a categoria na entidade produto para depois salvar o produto no banco no método
                insert ou update*/

            }

        }

}
