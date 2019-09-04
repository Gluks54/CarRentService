package com.example.CarRental.service;

import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public boolean clientExists(UUID clientId) {
        return  clientRepository.existsById(clientId);
    }

    public Client addClient(Client client) {
        ClientEntity clientEntity = clientRepository.save(map(client));
        return map(clientEntity);
    }

    public Client getClient(UUID clientId) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(clientId);
        if (!clientEntityOptional.isPresent()) {
            return null;
        }
        return map(clientEntityOptional.get());
    }

    public Client map(ClientEntity source) {
        return Client
                .builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .address(source.getAddress())
                .build();
    }

    public ClientEntity map(Client source) {
        return ClientEntity
                .builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .address(source.getAddress())
                .build();
    }
}
