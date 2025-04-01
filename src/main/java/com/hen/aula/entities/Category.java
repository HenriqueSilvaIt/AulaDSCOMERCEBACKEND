package com.hen.aula.entities;

import jakarta.persistence.*;

import java.util.HashSet;
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
}
