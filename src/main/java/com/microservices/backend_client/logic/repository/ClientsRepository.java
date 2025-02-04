package com.microservices.backend_client.logic.repository;

import com.microservices.backend_client.logic.entity.Clients;
import com.microservices.backend_client.logic.repository.customRepo.CustomClientsRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientsRepository extends MongoRepository<Clients, Long>, CustomClientsRepo {
}
