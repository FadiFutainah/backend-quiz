package maids.quiz.salesms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maids.quiz.salesms.model.Client;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportDto {
    Long totalNumberOfClients;
    List<Client> topSpendingClients;

}
