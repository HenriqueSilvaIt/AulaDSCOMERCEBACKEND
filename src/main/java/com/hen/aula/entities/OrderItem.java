package com.hen.aula.entities;



import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="tb_order_item")
public class OrderItem {

    @EmbeddedId /*é usada para indicar que um campo ou propriedade de uma
    entidade é uma chave primária composta, ou seja, uma chave que
    é formada por múltiplos campos de outra classe, que é incorporada na entidade.
*/
    private OrderItemPK id = new OrderItemPK();// QUANDO É classe
    // auxiliar tem que instanciar new  aqui

    private Integer quantity;
    private Double price;

    private OrderItem(){

    }

    public OrderItem(Order order, Product product, Integer quantity, Double price ) {

        id.setOrder(order); // atribui order dentro do id Orderitem pk
        id.setProduct(product); // atribui product dentro do  id Orderitem pk k
        this.quantity = quantity;
        this.price =price;
    }

    public Integer getQuantity() {
        return quantity;
    }


    // Pega objeto order que está dentro do OrderItemPk
    public Order getOrder() {
        return id.getOrder();
    }

    // modifica  objeto order que está dentro do OrderItemPk
    public void setOrder(Order order) {
        id.setOrder(order);
    }

    // Pega objeto product que está dentro do OrderItemPk
    public Product getProduct() {
        return id.getProduct();
    }

    // modifica  objeto product que está dentro do OrderItemPk
    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
