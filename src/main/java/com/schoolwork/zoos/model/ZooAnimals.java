package com.schoolwork.zoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "zooanimals", uniqueConstraints = @UniqueConstraint(columnNames = {"zooid", "animalid"}))
public class ZooAnimals extends Auditable implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "zooid")
    @JsonIgnoreProperties("zooanimals")
    private Zoo zoo;

    @Id
    @ManyToOne
    @JoinColumn(name = "animalid")
    @JsonIgnoreProperties("zooanimals")
    private Animal animal;

}
