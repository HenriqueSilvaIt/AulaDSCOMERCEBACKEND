package com.hen.aula.dto;

import com.hen.aula.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class ProductMinDTO {

    //Nos atributos são aplicados as anotation de validação de dados
    // da biliotec BeanValidation, PARA o dTO QUE Só vai lista n precisa de validação
    // do validation

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;


// Mesmo só tendo método get aqui precisa de construtor, se não, não vai gerar
    // a execeção correta na console, para que podemos trata-la
    public ProductMinDTO() {

    }

    // Construtor  com argumentos do DTO
    public ProductMinDTO(Long id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
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
    public ProductMinDTO(Product entity) {

        id = entity.getId(); // NESSE CASO n precisa colocar this.id como
        // no construtor acima, porque aqui o id do ProductDTO é diferente do id Product entity
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
