package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Membership;

@RestController
@RequestMapping(value="/membership")
public class MembershipResource extends AbstractController<Membership, Integer> {

}
