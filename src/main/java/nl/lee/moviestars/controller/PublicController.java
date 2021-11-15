package nl.lee.moviestars.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PublicController {

    @GetMapping(value = "/public")
    public String hello() {
        return "Hello everybody";
    }

}
