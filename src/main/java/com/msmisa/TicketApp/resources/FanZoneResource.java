package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanZone;

@RestController
@RequestMapping(value="/fanzone")
public class FanZoneResource extends AbstractController<FanZone, Integer> {

}
