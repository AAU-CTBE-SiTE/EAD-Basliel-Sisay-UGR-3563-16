package com.craft;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private Address address;
    private List<Subject> subjects = new ArrayList<>();

    public Student() {}
    public Student(int id, String name, Address address, List<Subject> subjects) {
        this.id = id; this.name = name; this.address = address; this.subjects = subjects;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }
    public String getDetails() { return "Student{id=" + id + ", name='" + name + "', address=" + address + ", subjects=" + subjects + "}"; }
}
