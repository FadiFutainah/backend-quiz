package maids.quiz.salesms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleTransactionDto {
    @NotNull
    @PositiveOrZero
    Integer productId;

    @NotNull
    @PositiveOrZero
    Double price;

    @NotNull
    @PositiveOrZero
    Long quantity;
}
