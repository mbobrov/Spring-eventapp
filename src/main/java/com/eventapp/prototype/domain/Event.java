package com.eventapp.prototype.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Event {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "events")
    private List<Performer> participants = new ArrayList<Performer>();

    @MapKey(name="id")
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Map<String, EventInstance> gigs = new HashMap<String, EventInstance>();

    public Event() {
    }

    public Event(String name, List<Performer> participants, Map<String, EventInstance> gigs) {
        this.name = name;
        this.participants = participants;
        this.gigs = gigs;
    }

    public Event(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Performer> getParticipants() {
        return participants;
    }

    public Map<String, EventInstance> getGigs() {
        return gigs;
    }

    public void addParticipant(Performer p) {
        participants.add(p);
    }

    public void addEventInstance(String name, EventInstance gig) {
        gigs.put(name, gig);
    }

}
