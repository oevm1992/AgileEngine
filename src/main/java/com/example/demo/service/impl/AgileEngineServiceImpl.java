package com.example.demo.service.impl;

import com.example.demo.components.WebClientComponent;
import com.example.demo.models.*;
import com.example.demo.models.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.AgileEngineService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@Log4j2
public class AgileEngineServiceImpl implements AgileEngineService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    WebClientComponent webClientComponent;

    public List<Image> search(String search){
        return imageRepository.search(search);
    }

    @Scheduled(cron = "${spring.application.schedule}")
    public String loadDataBase() {
        imageRepository.deleteAll();
        ResponseTokenDTO responseTokenDTO = getToken();
        ResponseImagesDTO responseImagesDTO = new ResponseImagesDTO();
        ResponseImageDetailDTO responseImageDetailDto;
        Integer pageCounter = 1;

        if(Objects.isNull( responseTokenDTO) || !responseTokenDTO.isAuth()){
            return "Error";
        }

        while(pageCounter.equals(1) ||  responseImagesDTO.isHasMore()){
            responseImagesDTO = getPage(pageCounter, responseTokenDTO.getToken());

            if(Objects.isNull( responseImagesDTO)){
                return "Error";
            }

            for (ImageDTO image:responseImagesDTO.getPictures()){
                responseImageDetailDto = getImagesDetails(responseTokenDTO.getToken(), image.getId());
                Image imag = Image.builder()
                        .author(responseImageDetailDto.getAuthor())
                        .camera(responseImageDetailDto.getCamera())
                        .croppedPicture(responseImageDetailDto.getCroppedPicture())
                        .fullPicture(responseImageDetailDto.getFullPicture())
                        .id(responseImageDetailDto.getId())
                        .tags(responseImageDetailDto.getTags())
                        .build();
                imageRepository.save(imag);
            }
            pageCounter++;
        }

        return "No errors";
    }

    private ResponseTokenDTO getToken() {
       WebClient webclient = webClientComponent.webClientBuilder("http://interview.agileengine.com/auth",headers(null), "AgileEngine");
        RequestTokenDTO requestTokenDTO = new RequestTokenDTO("23567b218376f79d9415");
        ClientResponse clientResponse = webClientComponent.postResponse(webclient, BodyInserters.fromPublisher(Mono.just(requestTokenDTO),RequestTokenDTO.class));
        return clientResponse.bodyToMono(ResponseTokenDTO.class).block();

    }

    private Consumer<HttpHeaders> headers(String accessToken){
        return headers -> {
            if(null != accessToken) {headers.set("Authorization","Bearer ".concat(accessToken));}
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
        };
    }

    public ResponseImagesDTO getPage(Integer pageCounter, String accessToken) {

        WebClient webClient = webClientComponent.webClientBuilder("http://interview.agileengine.com/images?page=".concat(pageCounter.toString()),  headers(accessToken), "AgileEngine");

        ClientResponse clientResponseImages = webClientComponent.getResponse(webClient);
        if(!clientResponseImages.statusCode().is2xxSuccessful()) {
            return null;
        }
        return clientResponseImages.bodyToMono(ResponseImagesDTO.class).block();
    }

    public ResponseImageDetailDTO getImagesDetails(String accessToken, String imageId) {

        WebClient webClient = webClientComponent.webClientBuilder( "http://interview.agileengine.com/images/".concat(imageId),  headers(accessToken), "AgileEngine");
        ClientResponse clientResponseImages = webClientComponent.getResponse(webClient);
        if(!clientResponseImages.statusCode().is2xxSuccessful()) {
            return null;
        }
        return clientResponseImages.bodyToMono(ResponseImageDetailDTO.class).block();
    }
}
