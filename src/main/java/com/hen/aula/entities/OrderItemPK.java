package com.hen.aula.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable /*Define que uma classe pode ser usada como um atributo
 de outra classe entidade, sem ser ela mesma uma entidade.*/
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id") // chave estrangeira dentro do product
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id") // chave estrangeira  dentro do order
    private Product product;

    public OrderItemPK(){

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Nesse caso o hashCode equals tem que comparar os 2  id order e product


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
