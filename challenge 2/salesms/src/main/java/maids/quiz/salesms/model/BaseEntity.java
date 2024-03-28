package maids.quiz.salesms.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected T id;
}
