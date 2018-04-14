package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.FanZoneCreationDTO;
import com.msmisa.TicketApp.dto.preview.CinemaPreviewDTO;
import com.msmisa.TicketApp.dto.preview.FanZonePreviewDTO;

@RestController
@RequestMapping(value="/fanzone")
public class FanZoneResource extends AbstractController<FanZone, Integer> {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FanZonePreviewDTO>> getAll(){
		List<FanZone> list = getDao().getAll();
		List<FanZonePreviewDTO> cinemaDtoList = list.stream().map(c -> convertToDto(c, FanZonePreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<FanZonePreviewDTO>>(cinemaDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanZonePreviewDTO>>(cinemaDtoList,HttpStatus.OK);

	}
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public CinemaPreviewDTO addNew(@DTO(value=FanZoneCreationDTO.class) FanZone cinema) {
		return convertToDto(getDao().insert(cinema), CinemaPreviewDTO.class);
	}
	
}
