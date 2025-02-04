package com.microservices.backend_client.logic.service;

import com.microservices.backend_client.commons.SequenceGeneratorService;
import com.microservices.backend_client.logic.entity.Clients;
import com.microservices.backend_client.logic.repository.ClientsRepository;
import com.microservices.shared_utils.statusResponces.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsCRUDOps {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private ClientsRepository clientsRepository;

    public StatusResponse createClient(Clients clients) {
        if (clients.getId() == null)
            clients.setId(sequenceGeneratorService.getSequenceNumber(Clients.CLIENT_SEQUENCE));

        if (clients.getMongoUrl() == null)
            clients.setMongoUrl("mongodb+srv://sypatil0803:Pos%402262@satishpatil.tx0oz.mongodb.net/springBootDefault?retryWrites=true&w=majority&appName=SatishPatil");

        return new StatusResponse(
                true,
                "Client Created Successfully",
                clientsRepository.save(clients)
        );
    }
}
