package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseImagesDTO {

    private List<ImageDTO> pictures;
    private int page;
    private int pageCount;
    private boolean hasMore;

}
