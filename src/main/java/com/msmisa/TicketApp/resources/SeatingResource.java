package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Seating;

@RestController
@RequestMapping(value="/seating")
public class SeatingResource extends AbstractController<Seating, Integer>{

}
