package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Hall;

@RestController
@RequestMapping(value="/hall")
public class HallResource extends AbstractController<Hall, Integer> {

}
