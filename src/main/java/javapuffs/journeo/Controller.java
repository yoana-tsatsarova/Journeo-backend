package javapuffs.journeo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class Controller {


    @GetMapping
    public String getmap() {
        return "Hello World";
    }
}
