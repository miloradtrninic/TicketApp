package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Bid;

@RestController
@RequestMapping(value="/bids")
public class BidResource extends AbstractController<Bid, Integer>{

}
