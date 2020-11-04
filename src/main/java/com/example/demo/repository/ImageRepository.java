package com.example.demo.repository;

import com.example.demo.models.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    @Query(value = "SELECT * FROM testdb.image WHERE id = ?1 or author = ?1 or camera = ?1 or tags = ?1 or cropped_picture = ?1 or full_picture = ?1",nativeQuery = true)
    List<Image> search(@Param("search") String search);
}
