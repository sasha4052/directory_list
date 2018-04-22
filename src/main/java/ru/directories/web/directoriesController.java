package ru.directories.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.directories.dom.Directory;
import ru.directories.rep.DirectoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class directoriesController {

    @Autowired
    DirectoryRepository rp;

    @PostMapping
    public Directory create(@RequestBody String  path){
        try {
            return rp.save(new Directory(path));
        }catch(NullPointerException e){
            return null;
        }
    }

    @GetMapping
    public List  findAll()
    {
        return rp.findAll();
    }

    @RequestMapping(path = {"/{id}"})
    public List  getFilesFromDirectory(@PathVariable("id") int id)
    {
        return Arrays.asList(rp.findOne(id).getFlist().split(";"));
    }

    /*@RequestMapping({"/test"})
    public List  findAll1()
    {
        return rp.findAll();
    }*/
}
