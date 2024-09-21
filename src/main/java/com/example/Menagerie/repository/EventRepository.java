package com.example.Menagerie.repository;

import com.example.Menagerie.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
