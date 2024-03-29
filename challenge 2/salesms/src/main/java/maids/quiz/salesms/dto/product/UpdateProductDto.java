package maids.quiz.salesms.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    String name;

    String description;

    @PositiveOrZero
    Integer quantity;

    @PositiveOrZero
    Double price;

    Set<Integer> categories;
}
