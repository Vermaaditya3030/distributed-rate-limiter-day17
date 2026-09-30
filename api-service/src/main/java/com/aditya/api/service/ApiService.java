package com.aditya.api.service;
import org.springframework.stereotype.Service; import org.springframework.web.client.RestClient;
@Service public class ApiService { private final RestClient client=RestClient.builder().baseUrl("http://rate-limit-service:8081").build(); public boolean allowed(String id){try{return client.post().uri(u->u.path("/api/rate-limit/check").queryParam("clientId",id).build()).retrieve().toBodilessEntity().getStatusCode().is2xxSuccessful();}catch(Exception e){return false;}} }
