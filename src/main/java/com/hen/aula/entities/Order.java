package com.hen.aula.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //deixar o instant (instante do tempo) no padrão UTC que é horário do londres
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;
    private OrderStatus status;

    //Relacionamento de pedido com cliente(usuário)
    @ManyToOne
    @JoinColumn(name= "client_id") // coluna que vai conter a chave estrangeira do usuário
    // na tabela pedido
    private User client;

    //Como podemos ter um pedido sem pagamento, n vamos criar o pagamento agora
    @OneToOne(mappedBy = "order", cascade  = CascadeType.ALL)
    private Payment payment;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items  = new HashSet<>();

    public Order () {

    }

    public Order (Long id, Instant moment, OrderStatus status, User client, Payment payment ) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Pega todos os itens
    public Set<OrderItem> getItems() {
        return items;
    }

    //Pega a lista de produtos associadas ao pedido, com isso temos
    // uma nova lista de Produtos associado a esse pedido
    // porque transformos a lista em  uma stream, usamos o map
    // e criamos uma nova lista de produtos associado ao pedido com .toList
    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
