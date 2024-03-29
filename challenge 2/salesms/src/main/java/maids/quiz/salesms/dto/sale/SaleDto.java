package maids.quiz.salesms.dto.sale;

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
public class SaleDto {
    @NotNull
    @PositiveOrZero
    Integer clientId;

    @NotNull
    @PositiveOrZero
    Integer sellerId;

    Set<SaleTransactionDto> transactions = new HashSet<>();
}
