package guru.springframework.spring6restmvc.entities;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID") // anotação que descreve que o gerador é o UUID
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // anotação do hibernate que diz qual o nome do gerador e qual é a estratégia para gera-lo
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false) // anotação que mapeia a coluna no banco com tamanho, o tipo da variavel, se pode ser atualizada e se pode ser nula.
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @NotNull
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
        category.getBeers().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBeers().remove(category);
    }


    /**
     * Specifies that the annotated field of property is a generated creation timestamp.
     * The timestamp is generated just once, when an entity instance is inserted in the database.
     */
    @CreationTimestamp
    private LocalDateTime createdDate;

    /**
     * Specifies that the annotated field of property is a generated update timestamp.
     * The timestamp is regenerated every time an entity instance is updated in the database.
     */
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
