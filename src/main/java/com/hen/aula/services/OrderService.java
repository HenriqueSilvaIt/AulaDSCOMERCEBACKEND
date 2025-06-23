package com.hen.aula.services;

import com.hen.aula.dto.*;
import com.hen.aula.entities.*;
import com.hen.aula.repositories.OrderItemRepository;
import com.hen.aula.repositories.OrderRepository;
import com.hen.aula.repositories.ProductRepository;
import com.hen.aula.services.execeptions.DataBaseException;
import com.hen.aula.services.execeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service // indica para o Spring que essa classe é um serviço
public class OrderService {

    @Autowired // injeção de depêndência
    private OrderRepository repository;// instanciando o repositóry
    // do product para pegar informações do produto do banco de dados

    @Autowired
    private ProductRepository  productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

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

        @Transactional // para garantir que faça tudo e depois salve
    public  OrderDTO insert( OrderDTO dto) {

            Order order = new Order();

            order.setMoment(Instant.now()); /*horário do pedido vai ser o horário atual*/
            order.setStatus(OrderStatus.WAITING_PAYMENT); /*o pedido
            que acabou de salvar vai ficar com o staus aguardando pagamento*/

            /*Pegando usário logado para salvar o nome do usuário no pedido */
            User user = userService.authenticated();
            order.setClient(user); /*estamos dizendo que o  usuário do pedido
            vai ser o usuário autenticado que pegamos no userService*/

            /*Coloca orderItem dentro do order*/
            for (OrderItemDTO itemDto : dto.getItems()) {
                /* na linha abaixo estamos criando uma entidade produto
                fazendo referencia com o id do produto que está na tabela orderItem
                por isso injetamos o productrepository auqi pra fazer a referencia
                com o ordreItemDTO
                 */
                Product product  =  productRepository.getReferenceById(itemDto.getProductId());

                /*Instanciando o orderiTEM Ja com argumento com base no construtor
                * e o produto colocamos o que instanciamo acim*/
                OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
                order.getItems().add(item);
                /*Precisa ter um repository do OrderItem
                * injetado aqui porque toda entidade associada
                *  precis ater o repository injetado para consegui
                * salvar no banco de dados no método insert*/
            }

            /*Salvando todas informações acima no banco de dados
            * na entidade order*/
            repository.save(order);
            orderItemRepository.saveAll(order.getItems());
            /*Precisa ter um repository do OrderItem
             * injetado aqui porque toda entidade associada
             *  precis ater o repository injetado para consegui
             * salvar no banco de dados no método insert*/

            // Retornando um nova OrderDto com esses dados atualizado no banco

            return new OrderDTO(order);

        }
}
