package maids.quiz.salesms.service;

import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.UpdateClientDto;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends CrudService<Client, Integer> {
    ClientRepository repository;
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
}
