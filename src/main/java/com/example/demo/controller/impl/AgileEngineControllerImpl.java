package com.example.demo.controller.impl;

import com.example.demo.controller.AgileEngineController;
import com.example.demo.models.entity.Image;
import com.example.demo.service.impl.AgileEngineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AgileEngineControllerImpl implements AgileEngineController {

    @Autowired
    AgileEngineServiceImpl agileEngineService;

    @GetMapping("/search/{searchTerm}")
    @Override
    public List<Image> search(@PathVariable String searchTerm) {
        return agileEngineService.search(searchTerm);
    }
}
