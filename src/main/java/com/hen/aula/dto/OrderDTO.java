package com.hen.aula.dto;

import com.hen.aula.entities.Order;
import com.hen.aula.entities.OrderItem;
import com.hen.aula.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private UserMinDTO user;
    private PaymentDTO paymentDTO;

    /* aqui entrar uma lista de itens   (orderItemDto)
     */
    @NotEmpty(message = "Deve ter no mínimo um item de pedido") /*tem que te ao menos uma categoria*/
    private List<OrderItemDTO> items = new ArrayList<>();


    public OrderDTO(Long id, Instant moment, OrderStatus status, UserMinDTO user, PaymentDTO paymentDTO, List<OrderItemDTO> items) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.user = user;
        this.paymentDTO = paymentDTO;
        this.items = items;

    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        user = new UserMinDTO(entity.getClient()); /*Quando é outro dto
        dentro desse (outro objeto no json), fazermos um new nome do objeto DTO
         passando como argumento entity.getClient() que é  a tabela referente a ele
         na entidade Orde*/
        paymentDTO = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment()); /*
           Como o payment pode ser nulo (um pedido que n está pago ainda) fazemo da forma acima se for nulo retorna nulo se n, retorna os dados
           de pagamento
        Quando é outro dto
        dentro desse (outro objeto no json), fazermos um new nome do objeto DTO
         passando como argumento entity.getClient() que é  a tabela referente a ele
         na entidade Orde*/
        for (OrderItem item: entity.getItems()) {
            items.add(new OrderItemDTO(item));
        }

    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public UserMinDTO getUser() {
        return user;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    /*total n é um atributo por isso fazemos ele com get para
    *  a requisição conseguir reconhece - l como campo*/
    public Double getTotal() {
         double sum = 0.0;
         for (OrderItemDTO itemDTO: items) {
             sum += itemDTO.getSubTotal(); /*para cada  coleção de items
             eu pego a minha variavel soma receb ela mesma mais o  subTotal de cada
             item*/
         }
         /* depois de calcular tudo no for retornamo o sum*/

        return sum;
    }
}
