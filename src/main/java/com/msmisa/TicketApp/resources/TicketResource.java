package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Ticket;

@RestController
@RequestMapping(value="/ticket")
public class TicketResource extends AbstractController<Ticket, Integer>{

}
