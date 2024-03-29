package maids.quiz.salesms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Integer> {
    @NotNull
    @Column(nullable = false)
    String name;

    String description;

    @ManyToMany
    @JsonIgnoreProperties(allowSetters = true)
    Set<Category> categories;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    Integer quantity;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    Double price;

    @ManyToMany(mappedBy = "products")
    Set<Sale> sales;
}
