package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Cinema;

@RestController
@RequestMapping(value="/cinema")
public class CinemaResurce extends AbstractController<Cinema, Integer>{

}
