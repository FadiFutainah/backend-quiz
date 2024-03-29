package maids.quiz.salesms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class Category extends BaseEntity<Integer> {
    @Size(min = 2)
    @Column(nullable = false, unique = true)
    String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    Set<Product> products = new HashSet<>();
}
