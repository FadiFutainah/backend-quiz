package maids.quiz.salesms.service;

import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.client.ClientReportDto;
import maids.quiz.salesms.dto.client.UpdateClientDto;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ClientService extends CrudService<Client, Integer> {
    @Autowired
    SaleService saleService;
    @Autowired
    ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
    }

    public ResponseEntity<ResponseDto<Client>> update(Integer id, UpdateClientDto resource) {
        Client client = modelMapper.map(resource, Client.class);
        Client savedClient = lookupResource(id);
//        TODO: improve on
        if (client.getAddress() != null) savedClient.setAddress(client.getAddress());
        if (client.getMobileNumber() != null) savedClient.setMobileNumber(client.getMobileNumber());
        if (client.getFirstname() != null) savedClient.setFirstname(client.getFirstname());
        if (client.getLastname() != null) savedClient.setLastname(client.getLastname());
        if (client.getEmail() != null) savedClient.setEmail(client.getEmail());

        return super.add(savedClient);
    }

    public ResponseEntity<ResponseDto<ClientReportDto>> report(Instant from, Instant to) {
        Long totalCount = clientRepository.countByCreatedAtBetween(from, to);
        List<Client> topSpendingClients = saleService.getClientsOrderByTotalSum();

        ClientReportDto clientReportDto = ClientReportDto.builder()
                .totalNumberOfClients(totalCount)
                .topSpendingClients(topSpendingClients)
                .build();

        return ResponseDto.response(clientReportDto);
    }
}
