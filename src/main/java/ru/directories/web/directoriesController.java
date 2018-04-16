package ru.directories.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.directories.dom.Directory;

@RestController
public class directoriesController {

    @RequestMapping("/state")
    public Directory getState(@RequestParam(value = "elvId", defaultValue = "1") String elvId)
    {
        // this.cabServ.addCabin(new Cabin(1));
        // System.out.println("Test ONE" + this.cabServ.getCabin(1));

        return new Directory();
    }
}
