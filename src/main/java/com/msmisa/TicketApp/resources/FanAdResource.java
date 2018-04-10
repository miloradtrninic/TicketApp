package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanAd;

@RestController
@RequestMapping(value="/fanad")
public class FanAdResource extends AbstractController<FanAd, Integer> {

}
