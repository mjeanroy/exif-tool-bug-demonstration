package com.example.demo.controller;

import com.example.demo.exiftool.Exiftool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping(value = "/")
public class DemoController {

    private final Exiftool exiftool;

    public DemoController(Exiftool exiftool) {
        this.exiftool = exiftool;
    }

    @GetMapping
    public ResponseEntity<HttpStatus> test() {
        File myFile = new File("IMG_0745.JPG");
        System.out.println(exiftool.getLong(myFile));
        System.out.println(exiftool.getLat(myFile));

        return ResponseEntity.noContent().build();
    }


}
