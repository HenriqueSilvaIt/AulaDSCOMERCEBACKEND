package com.hen.aula.entities;

public enum OrderStatus {
    WAITING_PAYMENT,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELED;

    //OrderStatus ele cria os atributos por número
    // começando por 0 porque é enum , tipo enumerado
    // para inserir no banco de dados tem que ser número

    // para enum n precisa criar hashcode e equals, porque por padrão ele já
    // comapra com equals

}
