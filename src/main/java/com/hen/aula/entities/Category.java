package com.hen.aula.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories") // na outra classe do muitos para muitos colocamos isso mapeado para "categories" que é o nome do atributo na outra classe relacionad
    private Set<Product> products = new HashSet<>();

    public Category() {

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name= name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProducts() {
        return products;
    }

/*Métodos equals e hash code tem que ter em todas entidade, para comparar por exemplo uma categoria com a outra, para eu ver se uma é igual a outra comparamos pelo Id
POR ISSO é importante criamos, o equals e hashcode  do id para comparar pelo id

iMPORATNTE n setar a opção de valores nulo, que n são nulo,
como podemos criar um objeto com Id nulo, então n vamos marcar essa opção*/
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
