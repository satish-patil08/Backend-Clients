package com.microservices.backend_client.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.microservices.shared_utils.statusResponces.StringStatusResponse;
import com.microservices.shared_utils.threadLocals.AuthTokenStorage;
import com.microservices.shared_utils.threadLocals.MongoConnectionStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.microservices.shared_utils.constants.GlobalConstants.*;

public class ClientFilter extends OncePerRequestFilter {

//    private String generateClientsMongoURL(String tenantHeader) {
//        // For example, if your Client Management Service is hosted at port 8082:
//        return "http://localhost:8082/clients/get-mongo-url?tenantId=" + tenantHeader;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthTokenStorage.setToken(request.getHeader("Authorization"));

        String tenantHeader = request.getHeader(CLIENT_HEADER);
        if (tenantHeader == null || tenantHeader.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Long clientId = new ObjectMapper().convertValue(tenantHeader, Long.class);
            String dbConnectionString;

            if (CLIENT_DB_CONNECTIONS.containsKey(clientId)) {
                dbConnectionString = CLIENT_DB_CONNECTIONS.get(clientId);
                MongoConnectionStorage.setConnection(dbConnectionString);
                filterChain.doFilter(request, response);
                MongoConnectionStorage.clear();
            } else {
                HttpResponse<String> stringHttpResponse = Unirest.get(generateClientsMongoURL(tenantHeader)).asString();
                if (stringHttpResponse == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    StringStatusResponse statusResponse = new ObjectMapper().readValue(stringHttpResponse.getBody(), StringStatusResponse.class);
                    if (!statusResponse.getSuccess()) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        dbConnectionString = statusResponse.getData().replace(CLIENT_REPLACEMENT, tenantHeader);
                        CLIENT_DB_CONNECTIONS.put(clientId, dbConnectionString);// Set the connection string into thread-local storage
                        MongoConnectionStorage.setConnection(dbConnectionString);
                        filterChain.doFilter(request, response);
                        MongoConnectionStorage.clear();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
