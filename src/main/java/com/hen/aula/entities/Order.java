package com.hen.aula.entities;

import jakarta.persistence.*;

import java.time.Instant;

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

    public Order () {

    }

    public Order (Long id, Instant moment, OrderStatus status) {
        this.id = id;
        this.moment = moment;
        this.status = status;
    }
}
