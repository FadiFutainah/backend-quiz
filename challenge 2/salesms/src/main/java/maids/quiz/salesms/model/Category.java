package maids.quiz.salesms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class Category extends BaseEntity<Integer> {
    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "categories")
    Set<Product> products;
}
