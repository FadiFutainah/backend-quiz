package maids.quiz.salesms.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Product;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDto {
    Long totalNumber;
    Long totalRevenue;
    List<Product> topSellingProducts = new ArrayList<>();
    List<Client> topPerformingSellers = new ArrayList<>();
}
