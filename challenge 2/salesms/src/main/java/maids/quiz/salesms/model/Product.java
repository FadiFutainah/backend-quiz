package maids.quiz.salesms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Integer> {
    @Column(nullable = false)
    String name;

    String description;

    @JsonIgnoreProperties(value = "products", allowSetters = true)
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Category> categories = new HashSet<>();

    @Column(nullable = false)
    Long quantity;

    @Column(nullable = false)
    Double price;

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate() {
        audit("UPDATE");
    }

    @PreRemove
    public void onPreRemove() {
        audit("DELETE");
    }

    private void audit(String operation) {
        final Logger log = LoggerFactory.getLogger(Client.class);
        log.info("performed " + operation + " on Client class - " + (new Date().getTime()));
        log.info("price is " + price);
    }
}
