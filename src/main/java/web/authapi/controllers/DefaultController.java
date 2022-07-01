package web.authapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*", maxAge = 3600)
@RestController
public class DefaultController {
    @GetMapping("/")
    public String home(){
        return "default route";
    }
}
