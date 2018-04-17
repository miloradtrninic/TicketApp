package com.msmisa.TicketApp.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
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

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.fan.FanZoneDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.FanZoneCreationDTO;
import com.msmisa.TicketApp.dto.preview.FanZonePreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.dto.update.FanZoneUpdate;

@RestController
@RequestMapping(value="/fanzone")
public class FanZoneResource extends AbstractController<FanZone, Integer> {
	
	@Autowired
	UserDao userDao;
	
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
	public ResponseEntity<FanZonePreviewDTO> addNew(@DTO(value=FanZoneCreationDTO.class) FanZone fanZone) {
		FanZone newZone = getDao().insert(fanZone);
		return new ResponseEntity<>(convertToDto(newZone, FanZonePreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/admins")
	public ResponseEntity<List<UserPreviewDTO>> getAdmins(@PathVariable("id") Integer id) {
		Set<User> admins = ((FanZoneDao)getDao()).getAdmins(id);
		List<UserPreviewDTO> userPrev = admins.stream().map(u -> modelMapper.map(u, UserPreviewDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<List<UserPreviewDTO>>(userPrev,HttpStatus.OK);
	}
	
	@PutMapping(value="/update",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FanZonePreviewDTO> update(@RequestBody FanZoneUpdate zoneUpdate) {
		FanZone zone = getDao().get(zoneUpdate.getId());
		List<User> admins = userDao.getAllIn(zoneUpdate.getAdminFKs());
		zone.setAdmin(new HashSet<>(admins));
		zone = getDao().update(zone);
		return new ResponseEntity<FanZonePreviewDTO>(convertToDto(zone, FanZonePreviewDTO.class), HttpStatus.OK);
	}
}
