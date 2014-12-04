package com.eventapp.prototype.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventForm {

    @NotEmpty
    @Size(max = 256)
    private String name;

    private List<Performer> participants = new ArrayList<Performer>();

    private Map<String, EventInstance> gigs = new HashMap<String, EventInstance>();

    public EventForm() {
    }

    public EventForm(String name, List<Performer> participants, Map<String, EventInstance> gigs) {
        this.name = name;
        this.participants = participants;
        this.gigs = gigs;
    }

    public EventForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Performer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Performer> participants) {
        this.participants = participants;
    }

    public Map<String, EventInstance> getGigs() {
        return gigs;
    }

    public void setGigs(Map<String, EventInstance> gigs) {
        this.gigs = gigs;
    }

    public void addParticipant(Performer p) {
        participants.add(p);
    }

    public void addEventInstance(String name, EventInstance gig) {
        gigs.put(name, gig);
    }

}
