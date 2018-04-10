package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Reservation;

@RestController
@RequestMapping(value="/reservation")
public class ReservationResource extends AbstractController<Reservation, Integer>{

}
