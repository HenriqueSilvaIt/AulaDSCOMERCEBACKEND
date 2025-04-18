package com.hen.aula.entities;







import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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

    @OneToMany(mappedBy = "id.product") // um produto para muitos pedidos, precisa colocar o mappedBy
    //para
    /*/ como no OneToMany temos que colcoar como que está o nome do  atributos  da classe One (Product) na classe do lado Many(muitos) OrderItem
 como no caso do OrderItem não tem o atributo product, o atributo product está dentro do id que está na clase OrderItemPK, temos que colocar id.product no mapped by para
 considerar o atributo product que está dentro da classe OrderItemPK*/
    private Set<OrderItem> items = new HashSet<>();


   public Product() {

    }

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
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

    // Pega os pedidos associado a esse produto
    public Set<OrderItem> getItems() {
       return items;
    }

    // Pega a lista de items associado a esse produto
    public List<Order> getOrders() {
        return items.stream().map(x -> x.getOrder()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
