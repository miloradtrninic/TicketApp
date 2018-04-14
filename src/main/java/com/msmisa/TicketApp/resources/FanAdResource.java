package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.dto.preview.FanAdPreviewDTO;

@RestController
@RequestMapping(value="/fanad")
public class FanAdResource extends AbstractController<FanAd, Integer> {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FanAdPreviewDTO>> getAll(){
		List<FanAd> list = getDao().getAll();
		List<FanAdPreviewDTO> fanadDtoList = convertToDto(list, FanAdPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<FanAdPreviewDTO>>(fanadDtoList,HttpStatus.OK);

	}
}
