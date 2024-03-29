package maids.quiz.salesms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SaleTransaction extends BaseEntity<Integer> {
    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnoreProperties(allowSetters = true)
    Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(allowSetters = true)
    Product product;

    Double price;
    Long quantity;
}
