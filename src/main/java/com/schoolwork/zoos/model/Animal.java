package com.schoolwork.zoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animal extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalid;

    private String animaltype;

    @ManyToMany
    @JoinTable(name = "zooanimals", joinColumns = @JoinColumn(name = "animalid"), inverseJoinColumns = @JoinColumn(name = "zooid"))
    @JsonIgnoreProperties("animals")
    private List<Zoo> zoos = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("animal")
    private List<ZooAnimals> zooanimals = new ArrayList<>();

    public Animal() {
    }

    public Animal(String animaltype) {
        this.animaltype = animaltype;
    }

    public long getAnimalid() {
        return animalid;
    }

    public void setAnimalid(long animalid) {
        this.animalid = animalid;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public void setAnimaltype(String animaltype) {
        this.animaltype = animaltype;
    }

    public List<Zoo> getZoos() {
        return zoos;
    }

    public void setZoos(List<Zoo> zoos) {
        this.zoos = zoos;
    }
}
