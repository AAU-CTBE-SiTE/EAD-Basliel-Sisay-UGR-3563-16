package com.site.ActorManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor{
    private int id;
    private String name;
    private String movieName;
    public String output(){
        return "ID: " + id + " | Name: " + name + " | Movie: " + movieName;
    }
}
