package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;

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
	@GetMapping(value="/{id}/admins", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserPreviewDTO>> getAuditoriumAdmins(@PathVariable("id") Integer id){
		Auditorium auditorium = getDao().get(id);

		List<UserPreviewDTO> adminList = auditorium.getAdmin()
										.stream()
										.map(e -> modelMapper.map(e, UserPreviewDTO.class))
										.collect(Collectors.toList());
		if(adminList.isEmpty())
			return new ResponseEntity<List<UserPreviewDTO>>(adminList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<UserPreviewDTO>>(adminList,HttpStatus.OK);
	}

}
