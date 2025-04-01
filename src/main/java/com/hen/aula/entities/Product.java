package com.hen.aula.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)//unique quer dizer que esse campo e-mail, n vai ter repetição
    // em nivel de banco, ele  n deixa haver email igual
    private String email;
    @Column(columnDefinition = "TEXT")// varchar pode até 255 caracteres, para poder ter mias colocamos como texto
    private String description;
    private Double price;
    private String imgUrl;


    @ManyToMany // Relacionamento
    @JoinTable(name = "tb_product_category", // É a tablea de junção que é criada para relacionamento muitos para muitos
            joinColumns = @JoinColumn(name = "product_id"), // Chave estrangeira dó product Id, Como essa classe é a classe product (classe que você está, o JoinColuns vai ser product id
// e o inverseJoinColumns vai ser o outro lado que é o category
            inverseJoinColumns = @JoinColumn(name = "category_id")) // Chave estrangeira do category id
    private Set<Category> categories = new HashSet<>();

    public Product() {

    }

    public Product(Long id, String name, String email, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Category> getCagetory(){
        return categories;
    }
}
