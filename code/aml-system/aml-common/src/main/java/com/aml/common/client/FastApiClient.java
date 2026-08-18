package com.aml.common.dto.client;

import com.aml.common.dto.request.FastApiRequest;
import com.aml.common.dto.response.FastApiResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FastApiClient {

    private final RestClient restClient;

    public FastApiClient(
            @Value("${fastapi.base-url}") String baseUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public FastApiResponse predict(
            FastApiRequest request
    ) {

        return restClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(FastApiResponse.class);
    }
}