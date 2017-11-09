package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Play;

@RestController
@RequestMapping(value="/play")
public class PlayResource extends AbstractController<Play, Integer>{

}
