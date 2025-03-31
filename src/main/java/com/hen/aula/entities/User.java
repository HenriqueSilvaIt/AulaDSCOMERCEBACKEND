package com.hen.aula.entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity /*Entidade  é o modelo do MVC*/
@Table(name = "tb_user") /*Diz que é uma table e conseguimos passar o nome da tabela*/
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*coloca
    o auto incremento para o id*/
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String password;

    //Como Pedido(Order) é uma lista vamos instanciar uma lista de Order com ArrayList<>()

    @OneToMany(mappedBy = "client") /*Como o nome do atributo user
    lá no outro lado do relacionamento é client, temos que colcoar
    mappedBy client, para dizer que essa é a chave primário do USER
    ser associada a chave estrangeira do Order (client_id*/
    private List<Order> orders = new ArrayList<>(); // como é lista
    // temos que criar um método get para  o order, set n criamos para lista

    public User() {

    }

    public User(Long id, String name, String email, String phone, LocalDate birthDate, String password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Lista para order como é lista
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
