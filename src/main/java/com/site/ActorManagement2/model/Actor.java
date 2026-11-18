package com.site.ActorManagement2.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@Entity
@Table(name = "actors")
@AllArgsConstructor
@NoArgsConstructor
public class Actor{
    @Id
    private int id;
    private String name;
    private String movieName;
}
