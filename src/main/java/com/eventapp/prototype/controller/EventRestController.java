package com.eventapp.prototype.controller;

import com.eventapp.prototype.domain.Event;
import com.eventapp.prototype.domain.EventForm;
import com.eventapp.prototype.domain.Performer;
import com.eventapp.prototype.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
public class EventRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRestController.class);
    private final EventService eventService;

    @Inject
    public EventRestController(final EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public Event createEvent(@RequestBody @Valid final EventForm form) {//}, BindingResult result) {
        LOGGER.debug("Received request to create Event with data={}", form);
        Event usr = new Event(form.getName(), form.getParticipants(), form.getGigs());
        Event u = eventService.create(usr);
        LOGGER.debug("Result of SAVE: "+u.toString());
        return u;
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public Event saveEvent(@PathVariable Long id, @RequestBody @Valid final Event form) {//}, BindingResult result) {
        LOGGER.debug("Received request to create Event with id {} and data={}", id, form);
        eventService.getEvent(id);
        Event u = eventService.save(form);
        LOGGER.debug("Result of SAVE: "+u.toString());
        return u;
    }

    @RequestMapping(value = "/performer/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Performer> checkPerformer(@PathVariable String name) {//}, BindingResult result) {
        LOGGER.debug("Received request to find Performers named similar to {}", name);
        List<Performer> result =eventService.checkPerformer(name);
        return result;
    }

    @RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable Long id) {//}, BindingResult result) {
        LOGGER.debug("Received delete of Event with id={}", id);
        eventService.deleteEvent(id);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> listEvents() {
        LOGGER.debug("Received request to list all events");
        return eventService.getList();
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable Long id) {
        LOGGER.debug("Received request to retrieve Event with id={}", id);
        Event evt = eventService.getEvent(id);
        return evt;
    }

    //@ExceptionHandler
    //@ResponseStatus(HttpStatus.CONFLICT)
    /*public String handleEventAlreadyExistsException(EventAlreadyExistsException e) {
        return e.getMessage();
    }*/

}
