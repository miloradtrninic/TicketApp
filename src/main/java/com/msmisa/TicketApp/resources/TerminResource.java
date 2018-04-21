package com.msmisa.TicketApp.resources;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.dao.hall.HallDao;
import com.msmisa.TicketApp.dao.projection.ProjectionDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.TerminCreationDTO;
import com.msmisa.TicketApp.dto.preview.TerminPreviewDTO;
import com.msmisa.TicketApp.dto.update.TerminUpdateDTO;

@RestController
@RequestMapping(value="/termin")
public class TerminResource extends AbstractController<Termin, Integer>{

	@Autowired
	ProjectionDao projectionDao;
	@Autowired
	HallDao hallDao;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TerminPreviewDTO>> getAll(){
		List<Termin> list = getDao().getAll();
		List<TerminPreviewDTO> terminDtoList = list.stream().map(c -> convertToDto(c, TerminPreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<TerminPreviewDTO>>(terminDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<TerminPreviewDTO>>(terminDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNew(@DTO(TerminCreationDTO.class) Termin termin) {
		return new ResponseEntity<TerminPreviewDTO>(convertToDto(getDao().insert(termin), TerminPreviewDTO.class), HttpStatus.OK);
	}



	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getId (@PathVariable(value="id") Integer id) {
		TerminPreviewDTO tp = modelMapper.map(getDao().get(id), TerminPreviewDTO.class);
		return new ResponseEntity<TerminPreviewDTO>(tp, HttpStatus.OK);
	}
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public TerminPreviewDTO update(@RequestBody TerminUpdateDTO entity){
		Termin termin = getDao().get(entity.getId());
		termin.setDate(entity.getDate());
		termin.setDiscount(entity.isDiscount());
		termin.setPrice(entity.getPrice());
		return convertToDto(getDao().update(termin), TerminPreviewDTO.class);
	}
	
}
