package com.eventapp.prototype.service;

import com.eventapp.prototype.domain.Event;
import com.eventapp.prototype.domain.Event;
import com.eventapp.prototype.domain.Performer;

import java.util.List;

public interface EventService {

    Event create(Event event);

    Event save(Event event);

    Event getEvent(Long eventId);

    void deleteEvent(Long eventId);

    List<Event> getList();

    public List<Performer> checkPerformer(String perfName);

}
