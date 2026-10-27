package com.gestion.eventos.api.service;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService{

    private final EventRepository eventRepository;
    private final ResourcePatternResolver resourcePatternResolver;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
