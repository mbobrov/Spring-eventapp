package com.eventapp.prototype.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EventInstance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne(fetch = FetchType.EAGER)
    private Event event;

    public EventInstance() {
    }

    public EventInstance(Date date, Venue venue) {
        this.date = date;
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
