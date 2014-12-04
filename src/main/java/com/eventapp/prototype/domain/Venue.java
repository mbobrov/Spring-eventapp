package com.eventapp.prototype.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venue {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "venue")
    List<EventInstance> events = new ArrayList<EventInstance>();

    public Venue() {
    }

    public Venue(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
