package maids.quiz.salesms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
public class Sale extends BaseEntity<Integer> {
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties(allowSetters = true)
    Client client;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties(allowSetters = true)
    Client seller;

    @ManyToMany
    @JsonIgnoreProperties(allowSetters = true)
    Set<Product> products;

    @Column(nullable = false)
    Double total;
}
