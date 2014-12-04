package com.eventapp.prototype.service;

import com.eventapp.prototype.domain.Event;
import com.eventapp.prototype.domain.Performer;
import com.eventapp.prototype.repository.EventRepository;
import com.eventapp.prototype.repository.PerformerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private final PerformerRepository performerRepository;
    private final EventRepository repository;

    @Inject
    public EventServiceImpl(final EventRepository repository, final PerformerRepository perfRep) {
        this.repository = repository;
        performerRepository = perfRep;
    }

    @Override
    @Transactional
    public Event create(@NotNull @Valid final Event event) {
        LOGGER.debug("Creating {}", event);
        //List<Event> existing = repository.findByName(event.getName());//.findOne(event.getId());
        /*if (checkName(event.getName())) {
            throw new EventAlreadyExistsException("Event with eventname "+event.getName()+" already exists!", event.getName());
        }*/
        return repository.save(event);
    }

    @Override
    @Transactional
    public Event save(@NotNull @Valid final Event event) {
        LOGGER.debug("Saving {}", event);
        return repository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getList() {
        LOGGER.debug("Retrieving the list of all events");
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Performer> checkPerformer(String perfName) {
        LOGGER.debug("Retrieving the list of all participants similar to: "+perfName);
        return performerRepository.findByNameContaining(perfName);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEvent(Long eventId) {
        LOGGER.debug("Retrieving event with ID "+eventId);
        return repository.findOne(eventId);
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId) {
        LOGGER.debug("Retrieving event with ID "+eventId);
        repository.delete(eventId);
    }

}
