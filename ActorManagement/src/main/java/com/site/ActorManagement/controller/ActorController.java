package com.site.ActorManagement.controller;

import com.site.ActorManagement.model.Actor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController{
    private List<Actor> actors = new ArrayList<>();
    public ActorController(){
        actors.add(new Actor (1, "John Snow", "Game Of Thrones"));
        actors.add(new Actor(2, "Chandler Bing", "Friends"));
        actors.add(new Actor(3, "Micheal Scott", "The Office"));
        actors.add(new Actor(4, "Charlie Kelly", "Everyday is sunny in philadelphia"));
        actors.add(new Actor(5, "Eric Cartman", "South Park"));
    }
    @PostMapping
    public Actor createActor(@RequestBody Actor newActor){
        actors.add(newActor);
        return newActor;
    }
    @GetMapping
    public List<Actor> getAllActors(){
        return actors;
    }
    @GetMapping("/{id}")
    public Actor getActorById(@PathVariable int id){
        int index = 0;
        while (index < actors.size()){
            Actor currentActor = actors.get(index);
            if (currentActor.getId() ==id){
                return currentActor;
            }
            index++;
        }
        return null;
    }


    @GetMapping(value = "/iterate", produces = "text/plain")
    public String iterateAllActors() {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < actors.size()){
            Actor currentActor = actors.get(index);
            result.append(currentActor.output()).append("\n");
            index++;
        }
        return result.toString();
    }
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable int id, @RequestBody Actor updatedActor){
        int index = 0;
        while (index < actors.size()){
            Actor currentActor = actors.get(index);
            if (currentActor.getId() ==id){
                currentActor.setName(updatedActor.getName());
                currentActor.setMovieName(updatedActor.getMovieName());
                return currentActor;
            }
            index++;
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public String deleteActor(@PathVariable int id){
        int index = 0;
        while (index < actors.size()){
            Actor currentActor = actors.get(index);
            if (currentActor.getId() ==id){
                actors.remove(index);
                return "Actor with ID " + id + " is deleted";
            }
            index++;
        }
        return "Actor with ID " + id + " is not found";
    }
}
