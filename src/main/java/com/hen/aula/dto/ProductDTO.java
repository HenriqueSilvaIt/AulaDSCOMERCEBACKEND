package com.hen.aula.dto;

import com.hen.aula.entities.Product;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;


    // Construtor padrão
    public ProductDTO() {

    }

    // Construtor  com argumentos do DTO
    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    //Construtor que vai receber os dados da entidade produto
    // isso facilita na hora de instanciar o DTO na camada service
    // porque n precisa passar todos argumentos do pruduto
    // dessa forma new ProductDTO(product.getId(), product.getName(), etc
    // dessa forma é só passar o produto
    // que já está estanciado como repository l no servic dessa form
    // new ProductDTO(product)
    public ProductDTO(Product entity) {
        id = entity.getId(); // NESSE CASO n precisa colocar this.id como
        // no construtor acima, porque aqui o id do ProductDTO é diferente do id Product entity
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
