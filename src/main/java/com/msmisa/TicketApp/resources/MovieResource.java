package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Movie;

@RestController
@RequestMapping(value="/movie")
public class MovieResource extends AbstractController<Movie, Integer>{

}
