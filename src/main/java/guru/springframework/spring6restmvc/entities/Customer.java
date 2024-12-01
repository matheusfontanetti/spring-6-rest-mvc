package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID") // anotação que descreve que o gerador é o UUID
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // anotação do hibernate que diz qual o nome do gerador e qual é a estratégia para gera-lo
   @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false) // anotação que mapeia a coluna no banco com tamanho, o tipo da variavel, se pode ser atualizada e se pode ser nula.
    //@JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
    private String name;

    @Column(length = 255)
    private String email;

    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @Builder.Default// por default ira construir um hashset vazio e não retornara null nesse caso
    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders = new HashSet<>();
}
