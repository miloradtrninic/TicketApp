package com.msmisa.TicketApp.resources;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.dao.hall.HallDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.HallPreviewDTO;

@RestController
@RequestMapping(value="/hall")
public class HallResource extends AbstractController<Hall, Integer> {

	@GetMapping(value="/byauditorim/{id}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getByAuditoriumID(@PathParam("id") Integer id) {
		HallDao hallDao = (HallDao) getDao();
		List<Hall> hallList = hallDao.getByAuditoriumId(id);
		List<HallPreviewDTO> hallPreview = convertToDto(hallList, HallPreviewDTO.class);
		if(hallPreview.isEmpty())
			return new ResponseEntity<List<HallPreviewDTO>>(hallPreview,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<HallPreviewDTO>>(hallPreview,HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HallPreviewDTO>> getAll(){
		List<Hall> list = getDao().getAll();
		List<HallPreviewDTO> hallDtoList = convertToDto(list, HallPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<HallPreviewDTO>>(hallDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public HallPreviewDTO addNew(@DTO(value=HallPreviewDTO.class) Hall hall) {
		return convertToDto(getDao().insert(hall), HallPreviewDTO.class);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public HallPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), HallPreviewDTO.class);
	}
	
	
}
