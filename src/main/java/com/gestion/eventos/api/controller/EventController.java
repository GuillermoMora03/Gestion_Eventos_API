package com.gestion.eventos.api.controller;

import com.gestion.eventos.api.domain.Event;
import com.gestion.eventos.api.dto.EventResponseDto;
import com.gestion.eventos.api.mapper.EventMapper;
import com.gestion.eventos.api.service.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final IEventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventService.findAll();

        return eventMapper.toEventResponseDtoList(events);
    }

    @PostMapping
}
