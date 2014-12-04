package com.eventapp.prototype.repository;

import com.eventapp.prototype.domain.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, Long> {

    public List<Performer> findByNameContaining(String name);

}
