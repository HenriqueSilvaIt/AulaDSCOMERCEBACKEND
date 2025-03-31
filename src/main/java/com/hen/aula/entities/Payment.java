package com.hen.aula.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;


    @OneToOne
    @MapsId // ESTAMOS DIZENDO que o mesmo
    // ID do ORDER(DO pedido) vai ser o mesmo ID do pagamento (payment_id)
    // por isso no relacional o H2 só vai criar a coluna ORDER_ID porque  são
    // as mesma
    // como  o relacionamento
    // é um para um o order aqui fica como atributo normal e n lista
    private Order order;

    public Payment() {

    }

    public Payment(Long id, Instant moment, Order order) {
        this.id = id;
        this.moment = moment;
        this.order = order;

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

    public Order getOrders() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
