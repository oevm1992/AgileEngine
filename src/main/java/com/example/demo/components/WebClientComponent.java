package com.example.demo.components;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.function.Consumer;

@Component("webClientComponent")
public class WebClientComponent {

    public WebClient webClientBuilder(String url, Consumer<HttpHeaders> headers, String userAgent){
        return WebClient
                .builder()
                .baseUrl(url)
                .defaultHeaders(headers)
                .defaultHeader("UserAgent",userAgent)
                .build();
    }

    public ClientResponse postResponse(WebClient webClient, BodyInserter<?, ? super ClientHttpRequest> body){
        return webClient.post()
                .body(body)
                .exchange()
                .block();
    }

    public ClientResponse getResponse(WebClient webClient){
        return webClient.get()
                .exchange()
                .block();
    }

}
