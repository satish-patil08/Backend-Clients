package com.microservices.backend_client.logic.service;

import com.microservices.backend_client.logic.entity.Clients;
import com.microservices.backend_client.logic.repository.ClientsRepository;
import com.microservices.shared_utils.statusResponces.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientMgmtService {

    @Autowired
    private ClientsRepository clientsRepo;

    public StatusResponse getMongoURL(String clientId) {
        System.out.println("RECEIVED_CLIENT_ID => " + clientId);

        if (!clientId.equals("null")) {
            Optional<Clients> optionalMongoURL = clientsRepo.findById(Long.parseLong(clientId));
            if (optionalMongoURL.isEmpty()) return new StatusResponse(
                    false,
                    "No client for the given ID"
            );

            if (optionalMongoURL.get().getMongoUrl() == null || optionalMongoURL.get().getMongoUrl().isEmpty())
                return new StatusResponse(
                        false,
                        "Mongo URL of given client does not exist"
                );

            return new StatusResponse(
                    true,
                    "Retrieved Mongo URL of Client",
                    optionalMongoURL.get().getMongoUrl()
            );
        } else return new StatusResponse(
                false,
                "No client ID found"
        );
    }
}
