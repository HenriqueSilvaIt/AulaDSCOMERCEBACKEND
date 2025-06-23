package com.hen.aula.dto;

import com.hen.aula.entities.OrderItem;

public class OrderItemDTO {

    private Long productId; /*aqui no caso de uso fala que é
    o nome do produto*/
    private String name;
    private Double price;
    private Integer quantity;

    /*subTotal é um dado calculado por isso não vamos colocar ele como atributo
    * aqui, vamos fazer um método depoi e um get pra ele*/

    public OrderItemDTO(Integer quantity, Double price, String name, Long productId) {
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.productId = productId;
    }

    public OrderItemDTO(OrderItem entity) {
        quantity = entity.getQuantity(); /*order item já tem a quantidade de produto*/
        price = entity.getPrice(); /*order item já tem o preço do produto*/
        name = entity.getProduct().getName(); /* pega nome do produto*/
        productId = entity.getProduct().getId(); /*entro no produto pelo orderItem
         e pego o id do produto com getId*/
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    /*como subtotal é um item calculado
    * temos que fazer com get para ele padroniza a geração do Json
    * se não fazer com esse nome no método ele n vai aparece
    * na requisição*/
    public Double getSubTotal() {
        return price * quantity;
    }
}
