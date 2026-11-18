package com.site.ActorManagement2.repository;

import com.site.ActorManagement2.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{
}
