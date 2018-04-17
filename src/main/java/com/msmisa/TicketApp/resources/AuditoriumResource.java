package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;

@RestController
@RequestMapping(value="/auditorium")
public class AuditoriumResource extends AbstractController<Auditorium, Integer>  {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuditoriumPreviewDTO>> getAll(){
		List<Auditorium> list = getDao().getAll();
		List<AuditoriumPreviewDTO> audDtoList = convertToDto(list, AuditoriumPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.OK);
	}
	@GetMapping(value="admin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuditoriumPreviewDTO>> getAdminAuds(){
		/* TODO Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Auditorium> list = ((AuditoriumDao) getDao()).getByAdmin(authentication.getName());*/
		List<Auditorium> list = ((AuditoriumDao) getDao()).getAll();
		List<AuditoriumPreviewDTO> audDtoList = convertToDto(list, AuditoriumPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.OK);
	}
}
