package com.example.demo.models.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {

    @Id
    private String id;

    @Column(name="AUTHOR")
    private String author;

    @Column(name="CAMERA")
    private String camera;

    @Column(name="TAGS")
    private String tags;

    @Column(name="CROPPED_PICTURE")
    private String croppedPicture;

    @Column(name="FULL_PICTURE")
    private String fullPicture;
}
