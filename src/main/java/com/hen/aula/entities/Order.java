package com.hen.aula.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
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
}
