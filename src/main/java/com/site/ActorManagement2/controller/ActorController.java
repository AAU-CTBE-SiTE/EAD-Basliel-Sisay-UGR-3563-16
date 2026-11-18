package com.site.ActorManagement2.controller;
import com.site.ActorManagement2.model.Actor;
import com.site.ActorManagement2.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController{
    @Autowired
    private ActorService actorService;
    @GetMapping
    public List<Actor> getAllActors(@RequestParam(required = false) String name, @RequestParam(required = false) String movie){
        return actorService.getAllActors(name, movie);
    }
    @GetMapping("/all")
    public List<Actor> getAllActors_(){
        return actorService.getAllActors_();
    }
    @GetMapping("/{id}")
    public Actor getActorById(@PathVariable int id){
        return actorService.getActorById(id);
    }
    @PostMapping
    public Actor createActor(@RequestBody Actor actor){
        return actorService.createActor(actor);
    }
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable int id, @RequestBody Actor actor){
        return actorService.updateActor(id, actor);
    }
    @DeleteMapping("/{id}")
    public String deleteActor(@PathVariable int id){
        return actorService.deleteActor(id);
    }
}
