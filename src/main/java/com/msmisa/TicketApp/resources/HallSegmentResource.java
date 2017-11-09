package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.HallSegment;

@RestController
@RequestMapping(value="/hallsegment")
public class HallSegmentResource extends AbstractController<HallSegment, Integer>{

}
