package com.hen.aula.dto;

import com.hen.aula.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class ProductDTO {

    //Nos atributos são aplicados as anotation de validação de dados
    // da biliotec BeanValidation

    private Long id;
    @Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo requirido") //Para nome tem que ser notEmpty e não notNull, porque as vezes você pode colocar um espaço
    // no nome, NotEmpty para garantir que o campo nome n esteja vazia
    // porém como usuário pode digitar um espaço entre nome e sobre nome, o ideal
    //  para garantir que n esteja vazia é o NotBlank
    private String name;
    @Size(min  = 10, message = "Descrição precisa ter no mínimo 10 caractres")
    @NotBlank
    private String description;
    @Positive(message = "O preço deve ser positivo")
    private Double price;
    private String imgUrl;


// Mesmo só tendo método get aqui precisa de construtor, se não, não vai gerar
    // a execeção correta na console, para que podemos trata-la
    public ProductDTO () {

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
