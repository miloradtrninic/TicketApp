package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Invitation;

@RestController
@RequestMapping(value="/invitation")
public class InivitationResource extends AbstractController<Invitation, Integer> {

}
