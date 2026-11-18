package com.site.ActorManagement2.service;

import com.site.ActorManagement2.model.Actor;
import com.site.ActorManagement2.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors(String name, String movie) {
        List<Actor> allActors = actorRepository.findAll();
        List<Actor> result = new ArrayList<>();
        int i = 0;
        while (i < allActors.size()) {
            Actor actor = allActors.get(i);
            boolean matchesName = (name == null || name.isEmpty() || actor.getName().equalsIgnoreCase(name));
            boolean matchesMovie = (movie == null || movie.isEmpty() || actor.getMovieName().equalsIgnoreCase(movie));
            if (matchesName && matchesMovie) {
                result.add(actor);
            }
            i++;
        }
        return result;
    }

    public List<Actor> getAllActors_() {
        List<Actor> allActors = actorRepository.findAll();
        List<Actor> result = new ArrayList<>();
        Iterator<Actor> iterator = allActors.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public Actor getActorById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor updateActor(int id, Actor updatedActor) {
        Actor existingActor = actorRepository.findById(id).orElse(null);
        if (existingActor != null) {
            existingActor.setName(updatedActor.getName());
            existingActor.setMovieName(updatedActor.getMovieName());
            return actorRepository.save(existingActor);
        }
        return null;
    }

    public String deleteActor(int id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            return "Actor with id " + id + " has been deleted successfully.";
        }
        return "Actor with id " + id + " was not found.";
    }
}
