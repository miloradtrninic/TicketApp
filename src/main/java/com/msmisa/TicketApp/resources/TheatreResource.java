package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Theatre;

@RestController
@RequestMapping(value="/theatre")
public class TheatreResource extends AbstractController<Theatre, Integer> {

}
