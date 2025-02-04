package com.microservices.backend_client.logic.controller;

import com.microservices.backend_client.logic.entity.Clients;
import com.microservices.backend_client.logic.service.ClientMgmtService;
import com.microservices.backend_client.logic.service.ClientsCRUDOps;
import com.microservices.shared_utils.statusResponces.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientMgmtService clientMgmtService;

    @Autowired
    private ClientsCRUDOps clientsCRUDOps;

    @PostMapping("/create")
    public StatusResponse createClient(@RequestBody Clients clients) {
        return clientsCRUDOps.createClient(clients);
    }

    @GetMapping("/get-mongo-url")
    public StatusResponse getMongoUrl(@RequestParam String clientId) {
        return clientMgmtService.getMongoURL(clientId);
    }
}
