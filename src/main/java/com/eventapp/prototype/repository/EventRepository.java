package com.eventapp.prototype.repository;

import com.eventapp.prototype.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findByNameLike(String name);

}
