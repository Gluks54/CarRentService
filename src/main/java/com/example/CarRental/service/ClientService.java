package com.example.CarRental.service;

import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public boolean clientExists(UUID clientId) {
        return  clientRepository.existsById(clientId);
    }

    public UUID addClient(Client client) {
        ClientEntity clientEntity = clientRepository.save(map(client));
        return clientEntity.getId();
    }

    public Client map(ClientEntity source) {
        return Client
                .builder()
                .name(source.getName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .address(source.getAddress())
                .build();
    }

    public ClientEntity map(Client source) {
        return ClientEntity
                .builder()
                .name(source.getName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .address(source.getAddress())
                .build();
    }
}
