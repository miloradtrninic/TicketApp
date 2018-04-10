package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanItem;

@RestController
@RequestMapping(value="/fanitem")
public class FanItemResource extends AbstractController<FanItem, Integer>{

}
