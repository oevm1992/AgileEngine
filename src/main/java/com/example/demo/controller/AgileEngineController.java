package com.example.demo.controller;

import com.example.demo.models.entity.Image;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AgileEngineController {

    public List<Image> search(@PathVariable String search);
}
