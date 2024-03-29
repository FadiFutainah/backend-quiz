package maids.quiz.salesms.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maids.quiz.salesms.dto.sale.SaleTransactionDto;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSaleDto {
    Set<SaleTransactionDto> transactions = new HashSet<>();
}
